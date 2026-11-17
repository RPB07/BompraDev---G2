import { StyleSheet, Text, View } from 'react-native';

import { colors, spacing, typography } from '../theme';

export function CartaoCategoria({ titulo, descricao }) {
  return (
    <View accessible accessibilityRole="summary" style={styles.card}>
      <Text style={styles.title}>{titulo}</Text>
      <Text style={styles.description}>{descricao}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  card: {
    gap: spacing.xs,
    padding: spacing.md,
    borderColor: colors.border,
    borderRadius: 12,
    borderWidth: 1,
    backgroundColor: colors.surface,
  },
  title: {
    ...typography.cardTitle,
    color: colors.primary,
  },
  description: {
    ...typography.body,
    color: colors.textMuted,
  },
});

