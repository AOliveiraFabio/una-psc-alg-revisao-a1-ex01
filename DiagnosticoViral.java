import java.text.Normalizer;
import java.util.Scanner;

public class DiagnosticoViral {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== QUESTIONÁRIO DE TRIAGEM VIRAL ===\n");

        System.out.print("Informe o seu nome: ");
        String nome = scanner.nextLine();

        int idade = perguntarIdade(scanner, "Informe a sua idade: ");

        System.out.println();
        String cartaoVacina = perguntarSIMNAO(scanner, "Seu cartão de vacina está em dia?");
        String sintomas = perguntarSIMNAO(scanner, "Teve algum dos sintomas recentemente? (dor de cabeça, febre, náusea, dor articular, gripe)");
        String contato = perguntarSIMNAO(scanner, "Teve contato com pessoas com sintomas gripais nos últimos dias?");
        String viagem = perguntarSIMNAO(scanner, "Está retornando de viagem realizada no exterior?");

        int porcentagem = calcularRisco(cartaoVacina, sintomas, contato, viagem);

        System.out.println("\n=== RESULTADO DO DIAGNÓSTICO ===");
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade + " anos");
        System.out.println("Cartão de vacina em dia: " + cartaoVacina);
        System.out.println("Teve sintomas recentemente: " + sintomas);
        System.out.println("Teve contato com pessoas infectadas: " + contato);
        System.out.println("Retornando de viagem: " + viagem);
        System.out.println("Probabilidade de infecção: " + porcentagem + "%");
        System.out.println("\nOrientação final:");
        System.out.println(gerarOrientacao(porcentagem, viagem));
        System.out.println("\n====================================");
    }

    private static int perguntarIdade(Scanner scanner, String pergunta) {
        while (true) {
            System.out.print(pergunta);
            String input = scanner.nextLine().trim();
            try {
                int idade = Integer.parseInt(input);
                if (idade >= 0 && idade <= 120) {
                    return idade;
                } else {
                    System.out.println("Idade inválida. Digite um valor entre 0 e 120.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite apenas números inteiros.\n");
            }
        }
    }

    private static String perguntarSIMNAO(Scanner scanner, String pergunta) {
        while (true) {
            System.out.print(pergunta + " (SIM/NAO): ");
            String resposta = normalizarResposta(scanner.nextLine());
            if (resposta.equals("SIM") || resposta.equals("NAO")) {
                return resposta;
            } else {
                System.out.println("Resposta inválida. Digite SIM ou NAO.\n");
            }
        }
    }

    private static String normalizarResposta(String input) {
        String semAcento = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return semAcento.trim().toUpperCase();
    }

    private static int calcularRisco(String cartaoVacina, String sintomas, String contato, String viagem) {
        int risco = 0;
        if (cartaoVacina.equals("NAO")) risco += 10;
        if (sintomas.equals("SIM")) risco += 30;
        if (contato.equals("SIM")) risco += 30;
        if (viagem.equals("SIM")) risco += 30;
        return risco;
    }

    private static String gerarOrientacao(int porcentagem, String viagem) {
        if (porcentagem <= 30) {
            if (viagem.equals("SIM")) {
                return "Você ficará sob observação por 05 dias.";
            } else {
                return "Paciente sob observação. Caso apareça algum sintoma, gentileza buscar assistência médica.";
            }
        } else if (porcentagem > 30 && porcentagem <= 60) {
            return "Paciente com risco de estar infectado. Gentileza aguardar em lockdown por 02 dias para ser acompanhado.";
        } else if (porcentagem > 60 && porcentagem < 90) {
            return "Paciente com alto risco de estar infectado. Gentileza aguardar em lockdown por 05 dias para ser acompanhado.";
        } else { // 90% ou mais
            return "Paciente crítico! Gentileza aguardar em lockdown por 10 dias para ser acompanhado.";
        }
    }
}
