package com.toonystank.moodyfishing.utils;

import net.kyori.adventure.text.Component;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class LogoManager {

    public static Component getLogoSplash() {
        String logoText = """

               ┏━┓┏━┓╋╋╋╋╋╋╋┏┓╋╋╋╋┏━━━┓╋╋╋┏┓
               ┃┃┗┛┃┃╋╋╋╋╋╋╋┃┃╋╋╋╋┃┏━━┛╋╋╋┃┃
               ┃┏┓┏┓┣━━┳━━┳━┛┣┓╋┏┓┃┗━━┳┳━━┫┗━┳┳━┓┏━━┓
               ┃┃┃┃┃┃┏┓┃┏┓┃┏┓┃┃╋┃┃┃┏━━╋┫━━┫┏┓┣┫┏┓┫┏┓┃
               ┃┃┃┃┃┃┗┛┃┗┛┃┗┛┃┗━┛┃┃┃╋╋┃┣━━┃┃┃┃┃┃┃┃┗┛┃
               ┗┛┗┛┗┻━━┻━━┻━━┻━┓┏┛┗┛╋╋┗┻━━┻┛┗┻┻┛┗┻━┓┃
               ╋╋╋╋╋╋╋╋╋╋╋╋╋╋┏━┛┃╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┏━┛┃
               ╋╋╋╋╋╋╋╋╋╋╋╋╋╋┗━━┛╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┗━━┛\s
                \s
                        By Edward v1            \s""";

        return format(logoText);
    }

    public static Component getLogoVenom() {
        String logoText = """

                █████████████████████████████████████████████████████████████████████▀█
                █▄─▀█▀─▄█─▄▄─█─▄▄─█▄─▄▄▀█▄─█─▄███▄─▄▄─█▄─▄█─▄▄▄▄█─█─█▄─▄█▄─▀█▄─▄█─▄▄▄▄█
                ██─█▄█─██─██─█─██─██─██─██▄─▄█████─▄████─██▄▄▄▄─█─▄─██─███─█▄▀─██─██▄─█
                ▀▄▄▄▀▄▄▄▀▄▄▄▄▀▄▄▄▄▀▄▄▄▄▀▀▀▄▄▄▀▀▀▀▄▄▄▀▀▀▄▄▄▀▄▄▄▄▄▀▄▀▄▀▄▄▄▀▄▄▄▀▀▄▄▀▄▄▄▄▄▀
                                                 By Edward v1            \s""";

        return format(logoText);
    }

    public static Component getLogoOutline() {
        String logoText = """

                                        _           ___ _     _     _            \s
                  /\\/\\   ___   ___   __| |_   _    / __(_)___| |__ (_)_ __   __ _\s
                 /    \\ / _ \\ / _ \\ / _` | | | |  / _\\ | / __| '_ \\| | '_ \\ / _` |
                / /\\/\\ \\ (_) | (_) | (_| | |_| | / /   | \\__ \\ | | | | | | | (_| |
                \\/    \\/\\___/ \\___/ \\__,_|\\__, | \\/    |_|___/_| |_|_|_| |_|\\__, |
                                          |___/                             |___/\s
                                         By Edward v1            \s""";

        return format(logoText);
    }
    public static Component getRandomLogo() {
        int random = (int) (Math.random() * 3);
        return switch (random) {
            case 1 -> Component.newline().append(getLogoVenom());
            case 2 -> Component.newline().append(getLogoOutline());
            default -> Component.newline().append(getLogoSplash());
        };
    }

    public static Component format(String text) {
        String[] colors = {
                "#CF203E", "#36A2EB", "#FFCE56", "#FF6384", "#4BC0C0",
                "#FF5733", "#8B78E6", "#49A65E", "#FFAC33", "#8E44AD"
        };

        // Generate a random index to select a color from the array
        Random random = new Random();
        int randomIndex = random.nextInt(colors.length);
        String randomColor = colors[randomIndex];

        return MessageUtils.format("&" +randomColor + "-#fff& " + text).appendNewline();
    }
}
