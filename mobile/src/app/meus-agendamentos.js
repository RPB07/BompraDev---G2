import { useCallback, useState } from 'react';
import { Redirect, useFocusEffect } from 'expo-router';
import { ScrollView, StyleSheet } from 'react-native';
import { ActivityIndicator, Card, Text } from 'react-native-paper';
import { useAuth } from '../context/AuthContext';
import { listarMeusAgendamentos } from '../services/agendaService';
import { consultarServico } from '../services/catalogoService';
import { colors } from '../theme';

const situacoes = { PENDENTE: 'Aguardando aprovação', APROVADO: 'Aprovado', RECUSADO: 'Recusado', CANCELADO: 'Cancelado', CONCLUIDO: 'Concluído' };
const formatarData = valor => new Intl.DateTimeFormat('pt-BR', { timeZone: 'America/Sao_Paulo', day: '2-digit', month: '2-digit', year: 'numeric', hour: '2-digit', minute: '2-digit' }).format(new Date(valor));

export default function MeusAgendamentos() {
  const { usuario, token, inicializando } = useAuth();
  const [agendamentos, setAgendamentos] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState(false);

  useFocusEffect(useCallback(() => {
    let ativo = true;
    async function carregar() {
      try {
        const lista = await listarMeusAgendamentos(token);
        const itens = await Promise.all(lista.map(async item => {
          try { const servico = await consultarServico(item.servicoId); return { ...item, titulo: servico.titulo }; }
          catch { return { ...item, titulo: 'Serviço indisponível no catálogo' }; }
        }));
        if (ativo) { setAgendamentos(itens); setErro(false); }
      } catch { if (ativo) setErro(true); }
      finally { if (ativo) setCarregando(false); }
    }
    if (token && usuario?.tipo !== 'ADMIN') carregar();
    return () => { ativo = false; };
  }, [token, usuario?.tipo]));

  if (inicializando) return <ActivityIndicator style={styles.carregamento} />;
  if (!usuario) return <Redirect href="/login" />;
  if (usuario.tipo === 'ADMIN') return <Redirect href="/perfil" />;
  return (
    <ScrollView contentContainerStyle={styles.conteudo}>
      <Text variant="titleLarge" style={styles.titulo}>Meus agendamentos</Text>
      <Text style={styles.texto}>Horários de Brasília.</Text>
      {carregando ? <ActivityIndicator /> : erro ? <Text>Não foi possível carregar seus agendamentos.</Text> :
        agendamentos.length === 0 ? <Text>Você ainda não fez solicitações.</Text> : agendamentos.map(item => (
          <Card key={item.id} style={styles.cartao}><Card.Content>
            <Text variant="titleMedium">{item.titulo}</Text>
            <Text>{formatarData(item.inicio)}</Text>
            <Text>{item.valorContratado == null ? 'Valor não registrado' : `Valor: R$ ${Number(item.valorContratado).toFixed(2).replace('.', ',')}`}</Text>
            <Text>{situacoes[item.situacao] ?? item.situacao}</Text>
          </Card.Content></Card>
        ))}
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  carregamento: { flex: 1 },
  conteudo: { flexGrow: 1, padding: 24, backgroundColor: colors.background },
  titulo: { color: colors.text, fontWeight: '700', marginBottom: 8 },
  texto: { marginBottom: 20 },
  cartao: { backgroundColor: colors.surface, marginBottom: 12 },
});
