import { useEffect, useState } from 'react';
import { router, useLocalSearchParams } from 'expo-router';
import { ScrollView, StyleSheet } from 'react-native';
import { ActivityIndicator, Button, Text } from 'react-native-paper';
import { useAuth } from '../../context/AuthContext';
import { consultarServico } from '../../services/catalogoService';
import { colors } from '../../theme';

export default function DetalheServico() {
  const { id } = useLocalSearchParams();
  const { usuario } = useAuth();
  const [servico, setServico] = useState(null);
  const [carregando, setCarregando] = useState(true);

  useEffect(() => {
    let ativo = true;
    async function carregar() {
      try { const resultado = await consultarServico(id); if (ativo) setServico(resultado); }
      catch { if (ativo) setServico(null); }
      finally { if (ativo) setCarregando(false); }
    }
    if (typeof id === 'string') carregar();
    return () => { ativo = false; };
  }, [id]);

  if (carregando) return <ActivityIndicator style={{ flex: 1 }} />;
  if (!servico) return <Text style={styles.conteudo}>Não foi possível carregar este serviço.</Text>;
  return (
    <ScrollView contentContainerStyle={styles.conteudo}>
      <Text variant="headlineSmall" style={styles.titulo}>{servico.titulo}</Text>
      <Text>{servico.categoria.nome}</Text>
      <Text style={styles.secao}>R$ {Number(servico.valor).toFixed(2).replace('.', ',')} · {servico.duracaoMinutos} min</Text>
      <Text variant="titleMedium" style={styles.secao}>Descrição</Text>
      <Text variant="bodyLarge">{servico.descricao}</Text>
      <Button mode="outlined" style={styles.secao}
        onPress={() => router.push({ pathname: '/perfil/[id]', params: { id: servico.providerId } })}>Ver perfil do prestador</Button>
      {usuario?.tipo === 'PROVIDER' && usuario.id === servico.providerId ? (
        <Button mode="contained" style={styles.secao}
          onPress={() => router.push({ pathname: '/servicos/formulario', params: { id: servico.id } })}>Editar serviço</Button>
      ) : null}
      <Text style={styles.aviso}>Agendamentos serão adicionados na próxima parte desta etapa.</Text>
    </ScrollView>
  );
}

const styles = StyleSheet.create({
  conteudo: { flexGrow: 1, padding: 24, backgroundColor: colors.background },
  titulo: { color: colors.text, fontWeight: '700', marginBottom: 8 },
  secao: { marginTop: 20 },
  aviso: { marginTop: 24, color: colors.textMuted },
});
