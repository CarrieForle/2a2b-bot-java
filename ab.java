import java.lang.Math;
import java.util.Scanner;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class ab {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();

        System.out.println("Welcome to AB game. Please choose your option.");
        System.out.println("(1) Play");
        System.out.println("(2) Challenge");
        System.out.println("(3) Turn-based game");
        System.out.println("(4) How to play");
        System.out.println("(5) Quit");
        System.out.print("> ");

        while (true) {
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    play(sc, rd);
                    break;
                case 2:
                    challenge(sc, rd);
                    break;
                case 3:
                    turn_based_game(sc, rd);
                    break;
                case 4:
                    print_help();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice\n");
            }

            if (choice == 1 || choice == 2 || choice == 4 || choice == 5) {
                break;
            }

            System.out.print("> ");
        }
    }

    static void play(Scanner sc, Random rd) {
        int try_count = 0;
        int guess = 0;
        int answer = get_random_number(rd);

        while (guess != answer) {
            try_count++;

            System.out.printf("#%d attempt. Please guess a number > ", try_count);

            guess = get_input(sc);

            System.out.printf("You guessed %04d, which is ", guess);

            int hint = get_hint(guess, answer);

            int a = get_a(hint);
            int b = get_b(hint);

            if (a > 0) {
                System.out.printf("%da", a);
            }

            if (b > 0) {
                System.out.printf("%db", b);
            }

            if (a == 0 && b == 0) {
                System.out.print("0");
            }

            System.out.print("\n\n");
        }

        System.out.printf(
                "You guessed it. The answer is %04d.\nYou got it in %d tries.\nDo you want to play again? (y/n)\n> ",
                answer, try_count);

        while (true) {
            String input = sc.next();

            if (input.equals("y")) {
                play(sc, rd);
                break;
            } else if (input.equals("n")) {
                break;
            }

            System.out.print("You must input 'y' or 'n'\n> ");
        }
    }

    static void challenge(Scanner sc, Random rd) {
        int answer = 1984;
        int try_count = 0;
        boolean is_automatic = true;
        List<Info> infos = new ArrayList<Info>();
        String input;

        if (false) {
            System.out.print(
                    "Hello! I am Bob, the computer bot that gonna guess your number :). Do you want to input your answer in case you forget it? I promise I would not read the answer in throughout the evaluation. (y/n)\n> ");

            input = sc.next();

            while (!input.equals("y") && !input.equals("n")) {
                System.out.print("You must input 'y' or 'n'\n> ");
                input = sc.next();
            }

            if (input.equals("y")) {
                System.out.print("Good! Input your answer > ");

                answer = get_input(sc);

                System.out.printf("Since the program knows the answer, should Bob deduce it automatically? (y/n)\n> ");

                input = sc.next();

                while (!input.equals("y") && !input.equals("n")) {
                    System.out.print("You must input 'y' or 'n'\n> ");
                    input = sc.next();
                }

                is_automatic = input.equals("y");
            }
        }

        int guess = -1;

        while (answer != guess) {
            try_count++;

            guess = evaluate(infos, rd);
            int a = 0;
            int b = 0;

            System.out.printf("#%d attempt. Bob guessed %04d", try_count, guess);

            if (is_automatic) {
                int hint = get_hint(guess, answer);

                a = get_a(hint);
                b = get_b(hint);

                System.out.print(", which is ");

                if (a > 0) {
                    System.out.printf("%da", a);
                }

                if (b > 0) {
                    System.out.printf("%db", b);
                }

                if (a == 0 && b == 0) {
                    System.out.print("0");
                }

                System.out.print("\n\n");
            } else {
                boolean guessed_correctly = false;

                while (true) {
                    System.out.print("\n> ");

                    input = sc.next();

                    if (input.equals("0")) {
                        System.out.println("..., which is 0");
                        break;
                    }

                    else if (input.length() == 4 &&
                            (input.charAt(1) == 'A' || input.charAt(1) == 'a') &&
                            (input.charAt(3) == 'B' || input.charAt(3) == 'b')) {
                        a = input.charAt(0) - '0';
                        b = input.charAt(2) - '0';

                        if (a > 4 || a < 0 || b > 4 || b < 0 || a + b > 4 || a + b < 0) {
                            System.out.println("Invalid input");
                            continue;
                        }

                        System.out.printf("..., which is %da%db\n", a, b);
                    } else if (input.length() == 2) {
                        if (input.charAt(1) == 'A' || input.charAt(1) == 'a') {
                            a = input.charAt(0) - '0';

                            if (a > 4 || a < 0) {
                                System.out.println("Invalid input");
                                continue;
                            }

                            if (a == 4) {
                                guessed_correctly = true;
                            }

                            System.out.printf("..., which is %da\n", a);
                        } else if (input.charAt(1) == 'B' || input.charAt(1) == 'b') {
                            b = input.charAt(0) - '0';

                            if (b > 4 || b < 0) {
                                System.out.println("Invalid input");
                                continue;
                            }

                            System.out.printf("..., which is %db\n", b);
                        }
                    } else {
                        System.out.println("Invalid input");
                        continue;
                    }

                    break;
                }

                if (guessed_correctly) {
                    break;
                }
            }

            infos.add(new Info(guess, a, b));
        }

        if (is_automatic) {
            System.out.printf(
                    "Bob guessed it. The answer is %04d.\nBob got it in %d tries.\n",
                    answer, try_count);
        } else {
            System.out.printf("Bob guessed it in %d tries.\n", try_count);
        }

        System.out.print("Do you want to play again? (y/n)\n> ");

        while (true) {
            input = sc.next();

            if (input.equals("y")) {
                challenge(sc, rd);
                break;
            } else if (input.equals("n")) {
                break;
            }

            System.out.print("You must input 'y' or 'n'\n> ");
        }
    }

    static void turn_based_game(Scanner sc, Random rd) {
        int player_turn = rd.nextInt(2); // player is 0; Bob is 1.
        int bob_answer = get_random_number(rd);
        int[] try_counts = new int[2];
        List<Info> infos = new ArrayList<Info>();
        String input;

        int guess = -1;

        System.out.println(
                "Please think of an 4 digit number where no digits are the same. We won't ask you to input it so please put it in mind. This is crucial for the game to progress. Press <enter> to if you have done it.");

        while (sc.hasNext("\n")) {
            sc.nextLine();
        }

        sc.nextLine();

        while (true) {
            try_counts[player_turn]++;

            if (player_turn == 1) {
                int a = 0;
                int b = 0;

                guess = evaluate(infos, rd);

                System.out.printf("Bob's #%d attempt. Bob guessed %04d", try_counts[1], guess);

                boolean guessed_correctly = false;

                while (true) {
                    System.out.print("\nxayb > ");

                    input = sc.next();

                    if (input.equals("0")) {
                        System.out.println("..., which is 0");
                        break;
                    }

                    else if (input.length() == 4 &&
                            (input.charAt(1) == 'A' || input.charAt(1) == 'a') &&
                            (input.charAt(3) == 'B' || input.charAt(3) == 'b')) {
                        a = input.charAt(0) - '0';
                        b = input.charAt(2) - '0';

                        if (a > 4 || a < 0 || b > 4 || b < 0 || a + b > 4 || a + b < 0) {
                            System.out.println("Invalid input");
                            continue;
                        }

                        System.out.printf("..., which is %da%db\n", a, b);
                    } else if (input.length() == 2) {
                        if (input.charAt(1) == 'A' || input.charAt(1) == 'a') {
                            a = input.charAt(0) - '0';

                            if (a > 4 || a < 0) {
                                System.out.println("Invalid input");
                                continue;
                            }

                            if (a == 4) {
                                guessed_correctly = true;
                            }

                            System.out.printf("..., which is %da\n", a);
                        } else if (input.charAt(1) == 'B' || input.charAt(1) == 'b') {
                            b = input.charAt(0) - '0';

                            if (b > 4 || b < 0) {
                                System.out.println("Invalid input");
                                continue;
                            }

                            System.out.printf("..., which is %db\n", b);
                        }
                    } else {
                        System.out.println("Invalid input");
                        continue;
                    }

                    break;
                }

                if (guessed_correctly) {
                    break;
                }

                infos.add(new Info(guess, a, b));
                player_turn = 0;
            } else {
                System.out.printf("Your #%d attempt. Guess a number > ", try_counts[0]);

                guess = get_input(sc);

                int hint = get_hint(guess, bob_answer);

                int a = get_a(hint);
                int b = get_b(hint);

                if (a == 4) {
                    break;
                }

                if (a > 0) {
                    System.out.printf("%da", a);
                }

                if (b > 0) {
                    System.out.printf("%db", b);
                }

                if (a == 0 && b == 0) {
                    System.out.print("0");
                }

                System.out.println();

                player_turn = 1;
            }
        }

        System.out.printf("Game over!\nBob's answer is %04d\n", bob_answer);

        if (player_turn == 1) {
            System.out.printf("Bob won! Bob guessed it in %d tries.\n", try_counts[1]);
        } else {
            System.out.printf("You won! You guessed it in %d tries.\n", try_counts[0]);
        }

        System.out.print("Do you want to play again? (y/n)\n> ");

        while (true) {
            input = sc.next();

            if (input.equals("y")) {
                turn_based_game(sc, rd);
                break;
            } else if (input.equals("n")) {
                break;
            }

            System.out.print("You must input 'y' or 'n'\n> ");
        }
    }

    static int get_hint(int guess, int answer) {
        if (guess == answer) {
            return 4;
        }

        int a = 0;
        int b = 0;

        guess *= 10;

        for (int i = 0; i < 4; i++) {
            guess /= 10;

            int answer_tmp = answer * 10;

            for (int j = 0; j < 4; j++) {
                answer_tmp /= 10;

                if (guess % 10 == answer_tmp % 10) {
                    if (i == j) {
                        a++;
                    } else {
                        b++;
                    }

                    break;
                }
            }
        }

        return (b << 3) | a;
    }

    static int get_a(int hint) {
        return hint & 0b111;
    }

    static int get_b(int hint) {
        return hint >> 3;
    }

    static int get_random_number(Random rd) {
        int[] result_in_array = new int[4];

        for (int i = 0; i < 4; i++) {
            boolean has_value = false;
            int rand = 0;

            do {
                has_value = false;
                rand = rd.nextInt(10);

                for (int j = 0; j < i; j++) {
                    if (rand == result_in_array[j]) {
                        has_value = true;
                        break;
                    }
                }
            } while (has_value);

            result_in_array[i] = rand;
        }

        int result = 0;

        for (int i = 0; i < 4; i++) {
            int multiplier = 1;

            for (int j = 0; j < 3 - i; j++) {
                multiplier *= 10;
            }

            result += result_in_array[i] * multiplier;
        }

        return result;
    }

    static int evaluate(List<Info> infos, Random rd) {
        if (infos.size() == 0) {
            return get_random_number(rd);
        }
        
        boolean is_found_all_number_in_one_info = false;
        boolean[] is_only_one_possible_numbers = new boolean[4];
        
        int[][] probabilities = new int[4][10];
        int precision = 1000;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                probabilities[i][j] = -1;
            }
        }

        for (int lmfao = 0; lmfao < infos.size(); lmfao++) {
            for (Info info : infos) {
                if (info.is_zero()) {
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            probabilities[j][info.get_digits(i)] = 0;
                        }
                    }

                    continue;
                }

                if (info.get_a() + info.get_b() == 4 && !is_found_all_number_in_one_info) {
                    is_found_all_number_in_one_info = true;

                    for (int i = 0; i < 10; i++) {
                        boolean is_in_number = true;

                        for (int j = 0; j < 4; j++) {
                            if (i == info.get_digits(j)) {
                                is_in_number = false;
                                break;
                            }
                        }

                        if (is_in_number) {
                            for (int j = 0; j < 4; j++) {
                                probabilities[j][i] = 0;
                            }
                        }
                    }
                }

                int possible_number_count = 4;
                int definite_number_count = 0;

                for (int i = 0; i < 4; i++) {
                    int digit = info.get_digits(i);

                    if (probabilities[i][digit] == 1000) {
                        definite_number_count++;
                        possible_number_count--;
                    } else if (probabilities[i][digit] == 0) {
                        possible_number_count--;
                    }
                }

                for (int i = 0; i < 4; i++) {
                    int digit = info.get_digits(i);

                    if (probabilities[i][digit] == 0) {
                        continue;
                    }

                    int calculated_probability = 0;

                    if (possible_number_count > 0) {
                        calculated_probability = (info.get_a() - definite_number_count) * precision
                                / possible_number_count;
                    }

                    if (probabilities[i][digit] < calculated_probability) {
                        probabilities[i][digit] = calculated_probability;
                    }

                    if (info.get_a() == 0) {
                        probabilities[i][digit] = 0;
                    } else if (calculated_probability == 1000) {
                        for (int j = 0; j < 10; j++) {
                            if (j == digit) {
                                continue;
                            }

                            probabilities[i][j] = 0;
                        }
                    }
                }

                int[] possible_candidate_at_digit_counts = new int[4];
                int[] possible_position_for_digit_counts = new int[4];

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (probabilities[i][info.get_digits(j)] != 0) {
                            possible_candidate_at_digit_counts[i]++;
                        }

                        if (probabilities[j][info.get_digits(i)] != 0) {
                            possible_position_for_digit_counts[i]++;
                        }
                    }
                }

                for (int i = 0; i < 4; i++) {
                    int digit = info.get_digits(i);

                    for (int j = 0; j < 4; j++) {
                        if (i == j || probabilities[j][digit] == 0 || possible_candidate_at_digit_counts[j] == 0
                                || possible_position_for_digit_counts[i] == 0) {
                            continue;
                        }

                        // System.out.printf("%d: pos(%d), can(%d)\n", digit, possible_position_for_digit_counts[i],
                        //         possible_candidate_at_digit_counts[j]);

                        int calculated_probability = info.get_b() * precision
                                / (4 * possible_position_for_digit_counts[i] * possible_candidate_at_digit_counts[j]);

                        if (probabilities[j][digit] < calculated_probability) {
                            probabilities[j][digit] = calculated_probability;
                        }

                        if (info.get_b() == 0) {
                            probabilities[j][digit] = 0;
                        } else if (calculated_probability == 1000) {
                            for (int k = 0; k < 10; k++) {
                                if (k == digit) {
                                    continue;
                                }

                                probabilities[j][k] = 0;
                            }
                        }
                    }
                }
            }


            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int possible_number_count = 0;
                    int the_possible_number = -1;

                    for (int k = 0; k < 10; k++) {
                        if (probabilities[j][k] != 0) {
                            possible_number_count++;
                            the_possible_number = k;

                            if (possible_number_count > 1) {
                                break;
                            }
                        }
                    }

                    if (possible_number_count == 1) {
                        for (int k = 0; k < 4; k++) {
                            probabilities[k][the_possible_number] = 0;
                        }

                        probabilities[j][the_possible_number] = 1000;
                        is_only_one_possible_numbers[j] = true;
                    } else if (possible_number_count == 0) {
                        System.out.printf("Every number is not possible in %dth digit? What is going on?\n", j + 1);
                        break;
                    }
                }
            }
        }

        for (int i = 0; i < infos.size(); i++) {
            for (int j = i + 1; j < infos.size(); j++) {
                if (infos.get(i).get_a() + infos.get(i).get_b() + infos.get(j).get_a() + infos.get(j).get_b() != 4) {
                    continue;
                }

                boolean is_filterable_info = true;
                boolean[] is_digit_in_either_infos = new boolean[10];

                for (int k = 0; k < 4; k++) {
                    if (!is_digit_in_either_infos[infos.get(i).get_digits(k)]) {
                        is_digit_in_either_infos[infos.get(i).get_digits(k)] = true;
                    } else {
                        is_filterable_info = false;
                        break;
                    }

                    if (!is_digit_in_either_infos[infos.get(j).get_digits(k)]) {
                        is_digit_in_either_infos[infos.get(j).get_digits(k)] = true;
                    } else {
                        is_filterable_info = false;
                        break;
                    }
                }

                if (is_filterable_info) {
                    for (int k = 0; k < 10; k++) {
                        if (!is_digit_in_either_infos[k]) {
                            for (int l = 0; l < 4; l++) {
                                probabilities[l][k] = 0;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.printf("===%d=", j);
            }

            System.out.println();

            for (int j = 0; j < 10; j++) {
                if (probabilities[i][j] == -1) {
                    System.out.print("   X ");
                } else {
                    System.out.printf("%4d ", probabilities[i][j]);
                }
            }
            System.out.println();
        }

        System.out.println("\n\n");

        boolean[] is_unknown_numbers = new boolean[10];
        int unknown_numbers_length = 0;
        int[][] number_in_probability_order = new int[4][10];
        int[] answer_in_array = new int[4];

        for (int[] arr : number_in_probability_order) {
            for (int i = 0; i < 10; i++) {
                arr[i] = i;
            }
        }

        for (int i = 0; i < 10; i++) {
            if (probabilities[0][i] == -1) {
                is_unknown_numbers[i] = true;
                unknown_numbers_length++;
            }
        }

        if (unknown_numbers_length > 0) {
            int known_numbers = 0;

            for (Info info : infos) {
                known_numbers += info.get_a() + info.get_b();
            }

            if (known_numbers >= 4) {
                for (int i = 0; i < 10; i++) {
                    if (is_unknown_numbers[i]) {
                        for (int j = 0; j < 4; j++) {
                            probabilities[j][i] = 0;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = j + 1; k < 10; k++) {
                    if (probabilities[i][number_in_probability_order[i][j]] < probabilities[i][number_in_probability_order[i][k]]) {
                        int tmp = number_in_probability_order[i][j];
                        number_in_probability_order[i][j] = number_in_probability_order[i][k];
                        number_in_probability_order[i][k] = tmp;
                    }
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.printf("%d ", number_in_probability_order[i][j]);
            }

            System.out.println("\n********************");
        }

        System.out.println();

        // Use zero filter strategy every 4 times
        if (infos.size() % 3 == 0) {
            System.out.println("\nBob is trying zero filter strategy!\n");

            int answer_iter = 0;
            int possible_number_position = -1;
            int impossible_numbers_length = 0;

            for (int i = 0; i < 4; i++) {
                if (is_only_one_possible_numbers[i]) {
                    continue;
                }

                int max_probability = 0;

                for (int j = 0; j < 10; j++) {
                    if (max_probability < probabilities[i][j]) {
                        max_probability = probabilities[i][j];
                        possible_number_position = i;
                        answer_in_array[i] = j;
                    }
                }

                if (max_probability > 0) {
                    break;
                }
            }

            for (int i = 0; i < 10; i++) {
                boolean is_impossible_number = true;

                for (int j = 0; j < 4; j++) {
                    if (probabilities[j][i] != 0) {
                        is_impossible_number = false;
                        break;
                    }
                }

                if (is_impossible_number) {
                    if (answer_iter == possible_number_position) {
                        answer_iter++;
                    }

                    answer_in_array[answer_iter++] = i;
                    impossible_numbers_length++;
                }

                if (impossible_numbers_length >= 3) {
                    return answer_in_array[0] * 1000 + answer_in_array[1] * 100 + answer_in_array[2] * 10
                            + answer_in_array[3];
                }
            }

            System.out.println("It did not work\n");
        }

        answer_in_array = get_most_probable_combination(probabilities, infos, 0, 0, unknown_numbers_length <= 0, rd);

        for (int i = 0; i < 4 && unknown_numbers_length > 0; i++) {
            while (true) {
                int num = rd.nextInt(10);

                if (is_unknown_numbers[num]) {
                    answer_in_array[i] = num;
                    is_unknown_numbers[num] = false;
                    unknown_numbers_length--;
                    break;
                }
            }
        }

        return answer_in_array[0] * 1000 + answer_in_array[1] * 100 + answer_in_array[2] * 10 + answer_in_array[3];
    }

    static int[] get_most_probable_combination(int[][] probabilities, List<Info> infos, int start_position,
            int end_position, boolean no_duplicate, Random rd) {
        long max = 0;
        int[] tmp_array = new int[4];
        int[][] candidates = new int[5][4];
        int candidate_length = 0;

        for (int i = 0; i < 10; i++) {
            tmp_array[0] = i;
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    continue;
                }

                tmp_array[1] = j;
                for (int k = 0; k < 10; k++) {
                    if (i == k || j == k) {
                        continue;
                    }

                    tmp_array[2] = k;
                    for (int l = 0; l < 10; l++) {
                        if (i == l || j == l || k == l) {
                            continue;
                        }

                        tmp_array[3] = l;
                        long calculated_probability = 1;

                        for (int m = 0; m < 4; m++) {
                            calculated_probability *= probabilities[m][tmp_array[m]];
                        }

                        if (max > calculated_probability || no_duplicate && guess_is_duplicated(tmp_array, infos)) {
                            continue;
                        }

                        if (candidate_length < 5 && max == calculated_probability) {
                            for (int m = 0; m < 4; m++) {
                                candidates[candidate_length][m] = tmp_array[m];
                            }

                            candidate_length++;
                            continue;
                        }

                        candidate_length = 1;
                        max = calculated_probability;

                        for (int m = 0; m < 4; m++) {
                            candidates[0][m] = tmp_array[m];
                        }

                    }
                }
            }
        }

        return candidates[rd.nextInt(candidate_length)];
    }

    static boolean guess_is_duplicated(int[] guess, List<Info> infos) {
        for (Info info : infos) {
            boolean is_same = true;

            for (int i = 0; i < 4; i++) {
                if (guess[i] != info.get_digits(i)) {
                    is_same = false;
                    break;
                }
            }

            if (is_same)
                return true;
        }

        return false;
    }

    static int get_input(Scanner sc) {
        int input_as_int = 0;

        while (true) {
            String input = sc.next();

            try {
                input_as_int = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("You must input a 4 digit mumber.\n> ");
                continue;
            }

            if (input.length() != 4) {
                System.out.print("You must input a 4 digit number prefixed with 0 if it's 3 digit.\n> ");
                continue;
            }

            char[] digit = new char[4];

            boolean is_valid_number = true;

            for (int i = 0; i < 4 && is_valid_number; i++) {
                for (int j = 0; j < i; j++) {
                    if (input.charAt(i) == digit[j]) {
                        is_valid_number = false;
                        break;
                    }
                }

                digit[i] = input.charAt(i);
            }

            if (is_valid_number) {
                break;
            } else {
                System.out.print("The number cannot have duplicate digits.\n> ");
            }
        }

        return input_as_int;
    }

    static void print_help() {
        System.out.print(
                "The player have to guess a number of 4 digits which are not duplicated from the opponent, which is called \"answer\". After each guess, the opponent will give hint based on what number did the player guessed.\n\nThe hint is of format 'xayb'. The 'x' before 'a' is a number indicating how many digit in the player's guess are right and correctly placed to the answer. The 'y' before 'b' is a number indicating how many digit in the player's guess are in the answer but not correctly placed. If x or y is 0, x and its 'a' or y and its 'b' will be omitted from the hint. If x and y are both 0, the hint will be \"0\".\n\nFor example:\n\nAnswer: 1234\nGuess: 1305\nHint: 1a1b\n\nAnswer: 1234\nGuess: 4321\nHint: 4b\n\nAnswer: 1234\nGuess: 5678\nHint: 0\n\nUtilise each hint from respective guesses and guess the number in the fewest attempts possible!\n\n");
    }
}

class Info {
    private final int[] digits = new int[4];
    public final int a;
    public final int b;

    public Info(int d1, int d2, int d3, int d4, int a, int b) {
        this.digits[0] = d1;
        this.digits[1] = d2;
        this.digits[2] = d3;
        this.digits[3] = d4;
        this.a = a;
        this.b = b;
    }

    public Info(int guess, int a, int b) {
        this.digits[0] = guess / 1000 % 10;
        this.digits[1] = guess / 100 % 10;
        this.digits[2] = guess / 10 % 10;
        this.digits[3] = guess % 10;
        this.a = a;
        this.b = b;
    }

    public int get_digits(int index) {
        return digits[index];
    }

    public boolean is_zero() {
        return a == 0 && b == 0;
    }

    public int get_a() {
        return a;
    }

    public int get_b() {
        return b;
    }
}