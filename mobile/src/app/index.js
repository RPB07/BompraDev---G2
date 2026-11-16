import { Link } from 'expo-router';
import { Pressable, ScrollView, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

import { CategoryCard } from '../components/CategoryCard';
import { colors, spacing, typography } from '../theme';

const categories = [
  {
    title: 'BOMPRADROP',
    description: 'Notícias, dicas e conteúdos rápidos sobre tecnologia.',
  },
  {
    title: 'BOMPRALAB',
    description: 'Guias, tutoriais e materiais para aprofundar conhecimentos.',
  },
  {
    title: 'BOMPRAVOZ',
    description: 'Enquetes e pesquisas para participação da comunidade.',
  },
  {
    title: 'BOMPRAPRÊMIO',
    description: 'Experiências promocionais e solicitações de resgate.',
  },
  {
    title: 'SERVIÇOS',
    description: 'Serviços de tecnologia oferecidos por providers.',
  },
];

export default function HomeScreen() {
  return (
    <SafeAreaView style={styles.safeArea}>
      <ScrollView contentContainerStyle={styles.content}>
        <View style={styles.header}>
          <Text style={styles.brand}>BompraDev</Text>
          <Text style={styles.subtitle}>
            Conteúdo, comunidade e serviços para quem vive tecnologia.
          </Text>
          <Link href="/login" asChild>
            <Pressable
              accessibilityRole="button"
              accessibilityLabel="Acessar conta"
              style={({ pressed }) => [styles.accessButton, pressed && styles.pressed]}
            >
              <Text style={styles.accessButtonText}>Acessar conta</Text>
            </Pressable>
          </Link>
        </View>

        <View style={styles.categories}>
          <Text style={styles.sectionTitle}>Explore a plataforma</Text>
          {categories.map((category) => (
            <CategoryCard
              key={category.title}
              title={category.title}
              description={category.description}
            />
          ))}
        </View>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: colors.primary,
  },
  content: {
    flexGrow: 1,
    backgroundColor: colors.background,
  },
  header: {
    gap: spacing.md,
    paddingHorizontal: spacing.lg,
    paddingBottom: spacing.xl,
    paddingTop: spacing.lg,
    backgroundColor: colors.primary,
  },
  brand: {
    ...typography.title,
    color: colors.onPrimary,
  },
  subtitle: {
    ...typography.body,
    color: colors.primaryMuted,
  },
  accessButton: {
    minHeight: 48,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: spacing.sm,
    borderRadius: 10,
    backgroundColor: colors.accent,
  },
  accessButtonText: {
    ...typography.button,
    color: colors.onAccent,
  },
  pressed: {
    opacity: 0.8,
  },
  categories: {
    gap: spacing.md,
    padding: spacing.lg,
  },
  sectionTitle: {
    ...typography.heading,
    color: colors.text,
  },
});

