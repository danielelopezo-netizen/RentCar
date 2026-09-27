package util;

/**
 * Utilidad matemática para el cálculo y verificación de números perfectos.
 * Aplica el principio de Responsabilidad Única (SRP), aislando la lógica matemática del modelo de negocio.
 */
public class NumeroPerfectoUtil {

    private NumeroPerfectoUtil() {
        // Constructor privado para evitar instanciación de clase de utilidad
    }

    /**
     * Determina si un número expresado en cadena de texto es un número perfecto.
     * Un número perfecto es aquel cuya suma de sus divisores propios es igual al mismo número.
     *
     * @param numeroStr Cadena que contiene el número a verificar.
     * @return true si es número perfecto, false de lo contrario o si el formato es inválido.
     */
    public static boolean esNumeroPerfecto(String numeroStr) {
        if (numeroStr == null || numeroStr.trim().isEmpty()) {
            return false;
        }
        try {
            long numero = Long.parseLong(numeroStr.trim());
            return esNumeroPerfecto(numero);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Determina si un número entero positivo es un número perfecto.
     *
     * @param numero Número entero a verificar.
     * @return true si la suma de divisores propios es igual a numero.
     */
    public static boolean esNumeroPerfecto(long numero) {
        if (numero <= 1) {
            return false;
        }

        long sumaDivisores = 1;
        long limite = (long) Math.sqrt(numero);

        for (long i = 2; i <= limite; i++) {
            if (numero % i == 0) {
                sumaDivisores += i;
                long divisorParalelo = numero / i;
                if (i != divisorParalelo) {
                    sumaDivisores += divisorParalelo;
                }
            }
        }

        return sumaDivisores == numero;
    }
}
