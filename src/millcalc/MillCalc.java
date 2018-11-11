package millcalc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class MillCalc {

    public static ArrayList<String> deck = new ArrayList<>();
    public static ArrayList<String> inPlay = new ArrayList<>();
    public static ArrayList<String> inHand = new ArrayList<>();
    public static ArrayList<String> inGraveyard = new ArrayList<>();

    public static Random rand = new Random();
    public static Scanner scan = new Scanner(System.in);

    public static int iterations = 0;

    public static int aCards;
    public static int aCardsInPlay;
    public static int aCardsInHand;
    public static int cardsInPlay;
    public static int cardsInHand;
    public static int cardsInGraveyard;

    public static void initialize() {
        for (int i = 0; i < 99; i++) {
            deck.add(i, "Card " + (i + 1));
        }
    }

    public static void addACards() {
        System.out.println("Enter number of α-Cards:");
        aCards = scan.nextInt();
        ArrayList<Integer> tracker = new ArrayList<>();
        for (int i = 0; i < aCards; i++) {
            deck.set(i, deck.get(i) + ": α-Card " + (i + 1));
        }
    }

    public static void shuffle() {
        Collections.shuffle(deck, rand);
    }

    public static void setupGame() {
        scan.reset();

        System.out.println("Enter number of NON-α-Cards in play:");
        cardsInPlay = scan.nextInt();
        //add input checking later
//        while(inPlay.size()+inHand.size()+inGraveyard.size() > deck.size()){
//            System.out.println("Out of Bounds Entry, Retry:");
//            scan.reset();
//            cardsInPlay = scan.nextInt();
//        }
        int j = 0;
        while ((inPlay.size() < cardsInPlay) && (deck.size() > 2)) {
            if (!deck.get(j).contains("α-Card")) {
                inPlay.add(deck.get(j));
                deck.remove(j);
                deck.trimToSize();
            } else {
                j++;
            }
        }

        scan.reset();

        System.out.println("Enter number of α-Cards in play:");
        aCardsInPlay = scan.nextInt();
        while (aCardsInPlay > aCards) {
            System.out.println("Out of Bounds Entry, Retry:");
            scan.reset();
            aCardsInPlay = scan.nextInt();
        }
        j = 0;
        while (inPlay.size() < (cardsInPlay + aCardsInPlay)) {
            if (deck.get(j).contains("α-Card")) {
                inPlay.add(deck.get(j));
                deck.remove(j);
                deck.trimToSize();
            } else {
                j++;
            }
        }

        scan.reset();

        System.out.println("Enter number of NON-α-Cards in hand:");
        cardsInHand = scan.nextInt();
        j = 0;
        while (inHand.size() < cardsInHand) {
            if (!deck.get(j).contains("α-Card")) {
                inHand.add(deck.get(j));
                deck.remove(j);
                deck.trimToSize();
            } else {
                j++;
            }
        }

        scan.reset();

        System.out.println("Enter number of α-Cards in hand:");
        aCardsInHand = scan.nextInt();
        while (aCardsInHand > (aCards - aCardsInHand)) {
            System.out.println("Out of Bounds Entry, Retry:");
            scan.reset();
            aCardsInHand = scan.nextInt();
        }
        j = 0;
        while (inHand.size() < (cardsInHand + aCardsInHand)) {
            if (deck.get(j).contains("α-Card")) {
                inHand.add(deck.get(j));
                deck.remove(j);
                deck.trimToSize();
            } else {
                j++;
            }
        }

        scan.reset();

        System.out.println("Enter number of cards in graveyard:");
        cardsInGraveyard = scan.nextInt();
        j = 0;
        while (inGraveyard.size() < cardsInGraveyard) {
            if (!deck.get(j).contains("α-Card")) {
                inGraveyard.add(deck.get(j));
                deck.remove(j);
                deck.trimToSize();
            } else {
                j++;
            }
        }

        scan.reset();
    }

    public static void mill() {
        int j = 0;
        int targetDeckSize = aCards - aCardsInPlay - aCardsInHand;
        while (deck.size() > targetDeckSize) {
            inGraveyard.add(deck.get(j));
            deck.remove(j);
            deck.trimToSize();

            if (inGraveyard.get(inGraveyard.size() - 1).contains("α-Card")) {
                for (int i = 0; i < inGraveyard.size(); i++) {
                    deck.add(inGraveyard.get(i));
                    inGraveyard.remove(i);
                    inGraveyard.trimToSize();
                }
                shuffle();
                iterations++;
                if (iterations % 1000 == 0) {
                    System.out.println(iterations);
                }
            }
        }

        boolean check = false;
        for (int i = 0; i < deck.size(); i++) {
            if (!deck.get(i).contains("α-Card")) {
                check = true;
            }
        }
        if (check == true) {
            for (int i = 0; i < inGraveyard.size(); i++) {
                deck.add(inGraveyard.get(i));
                inGraveyard.remove(i);
                inGraveyard.trimToSize();
                shuffle();
            }
            mill();
        }
    }

    public static void main(String[] args) {
        System.out.println("Given a deck of 99 cards, with\nX cards in hand\nY cards in play\nZ cards in the graveyard\nAnd given α = the number of cards in the total deck that shuffle the graveyard back into the deck\n(i.e. [[Kozilek, Butcher of Truth]], [[Ulamog, the Infinite Gyre]], etc.]])\nHow many iterations would it take to mill the deck via infinite milling 1 card at a time until just the graveyard-shuffling cards (α-Cards) remain in the deck?\n");

        initialize();
        addACards();

        System.out.println("\nα-Cards = " + aCards);

        System.out.println("\nIn Deck Pre-Shuffle:");
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i));
        }

        shuffle();

        System.out.println("\nIn Deck Post-Shuffle:");
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i));
        }

        System.out.println("");

        setupGame();

        System.out.println("");
        for (int i = 0; i < inPlay.size(); i++) {
            System.out.println(inPlay.get(i));
        }

        System.out.println("");
        for (int i = 0; i < inHand.size(); i++) {
            System.out.println(inHand.get(i));
        }

        System.out.println("");
        for (int i = 0; i < inGraveyard.size(); i++) {
            System.out.println(inGraveyard.get(i));
        }

        shuffle();

        System.out.println("\nIn Deck Post-SetUp:");
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i));
        }

        System.out.println("\nMilling");
        mill();

        System.out.println("\nIn Deck Post-Mill:");
        for (int i = 0; i < deck.size(); i++) {
            System.out.println(deck.get(i));
        }

        System.out.println("\nIn Graveyard Post-Mill:");
        for (int i = 0; i < inGraveyard.size(); i++) {
            System.out.println(inGraveyard.get(i));
        }

        System.out.println("\nTotal Deck Size = " + (deck.size() + inPlay.size() + inHand.size() + inGraveyard.size()));

        System.out.println("\nTotal Iterations = " + iterations);
    }
}
