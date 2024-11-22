package fr.uvsq.poo.compte;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    private static HashMap<Integer, Account> accounts = new HashMap<>();
    private static int accountCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("=== Gestion des Comptes Bancaires ===");
        do {
           
            System.out.println("\n1. Créer un compte");
            System.out.println("2. Afficher le solde d'un compte");
            System.out.println("3. Créditer un compte");
            System.out.println("4. Débiter un compte");
            System.out.println("5. Transférer entre deux comptes");
            System.out.println("6. Quitter");
            System.out.print("Choisissez une option : ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> createAccount(scanner);
                case 2 -> displayBalance(scanner);
                case 3 -> creditAccount(scanner);
                case 4 -> debitAccount(scanner);
                case 5 -> transferBetweenAccounts(scanner);
                case 6 -> System.out.println("Merci d'avoir utilisé l'application !");
                default -> System.out.println("Option invalide, veuillez réessayer.");
            }
        } while (choice != 6);  

        scanner.close();
    }

   
    private static void createAccount(Scanner scanner) {
        System.out.print("Entrez le solde initial : ");
        BigDecimal initialBalance = scanner.nextBigDecimal();
        try {
            Account account = new Account(initialBalance);
            accounts.put(accountCounter, account);
            System.out.println("Compte créé avec succès ! Numéro du compte : " + accountCounter);
            accountCounter++;
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

 
    private static void displayBalance(Scanner scanner) {
        System.out.print("Entrez le numéro du compte : ");
        int accountId = scanner.nextInt();
        Account account = accounts.get(accountId);

        if (account != null) {
            System.out.println("Le solde du compte " + accountId + " est : " + account.getBalance());
        } else {
            System.err.println("Compte introuvable.");
        }
    }

    
    private static void creditAccount(Scanner scanner) {
        System.out.print("Entrez le numéro du compte à créditer : ");
        int accountId = scanner.nextInt();
        Account account = accounts.get(accountId);

        if (account != null) {
            System.out.print("Entrez le montant à créditer : ");
            BigDecimal amount = scanner.nextBigDecimal();
            try {
                account.credit(amount);
                System.out.println("Le compte " + accountId + " a été crédité de " + amount + ". Nouveau solde : " + account.getBalance());
            } catch (IllegalArgumentException e) {
                System.err.println("Erreur : " + e.getMessage());
            }
        } else {
            System.err.println("Compte introuvable.");
        }
    }

   
    private static void debitAccount(Scanner scanner) {
        System.out.print("Entrez le numéro du compte à débiter : ");
        int accountId = scanner.nextInt();
        Account account = accounts.get(accountId);

        if (account != null) {
            System.out.print("Entrez le montant à débiter : ");
            BigDecimal amount = scanner.nextBigDecimal();
            try {
                account.debit(amount);
                System.out.println("Le compte " + accountId + " a été débité de " + amount + ". Nouveau solde : " + account.getBalance());
            } catch (IllegalArgumentException e) {
                System.err.println("Erreur : " + e.getMessage());
            }
        } else {
            System.err.println("Compte introuvable.");
        }
    }

   
    private static void transferBetweenAccounts(Scanner scanner) {
        System.out.print("Entrez le numéro du compte source : ");
        int sourceAccountId = scanner.nextInt();
        System.out.print("Entrez le numéro du compte destination : ");
        int targetAccountId = scanner.nextInt();

        Account sourceAccount = accounts.get(sourceAccountId);
        Account targetAccount = accounts.get(targetAccountId);

        if (sourceAccount != null && targetAccount != null) {
            System.out.print("Entrez le montant à transférer : ");
            BigDecimal amount = scanner.nextBigDecimal();
            try {
                sourceAccount.transfer(amount, targetAccount);
                System.out.println("Transfert réussi ! Nouveau solde du compte source : " + sourceAccount.getBalance());
                System.out.println("Nouveau solde du compte destination : " + targetAccount.getBalance());
            } catch (IllegalArgumentException e) {
                System.err.println("Erreur : " + e.getMessage());
            }
        } else {
            System.err.println("Un ou les deux comptes sont introuvables.");
        }
    }
}
