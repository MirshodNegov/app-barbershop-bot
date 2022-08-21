package com.example.barbershopqueuebotapi;

import com.example.barbershopqueuebotapi.model.Days;
import com.example.barbershopqueuebotapi.model.Hours;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {
    private static final String username = "@JamaBarberOnlineBot";
    private static final String token = "5402312380:AAGfO_5Nqirsp-vV3WDzHR00VaC6svUOAjA";
    private static int level = 0;
    private static String service = "";
    private static String date = "";
    private static String hour = "";
    private static String phoneNum = "";
    private static String name = "";

    private static final Altegio altegio = new Altegio();

    @Override
    public void onUpdateReceived(Update update) {
        Message message;
        String chatId = "";
        String text = "";
        String data = "";
        String phoneNumber = "";
        Integer messageId = null;
        String inlineMessageId = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            chatId = callbackQuery.getMessage().getChatId().toString();
            messageId = callbackQuery.getMessage().getMessageId();
            data = callbackQuery.getData();
            inlineMessageId = callbackQuery.getInlineMessageId();
            text = callbackQuery.getMessage().getText();
        } else if (update.hasMessage()) {
            message = update.getMessage();
            chatId = message.getChatId().toString();
            if (message.hasText()) {
                text = message.getText();

                if (text.equalsIgnoreCase("/start")) {
                    level = 0;
                }

            } else if (message.hasContact()) {
                text = message.getContact().getPhoneNumber();
                phoneNum = message.getContact().getPhoneNumber();
                name = message.getContact().getFirstName();
            }
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
        editMarkup.setChatId(chatId);


        switch (level) {
            case 0:
                sendMessage.setText("Добро пожаловать в Джамшид Джуракулов Барбершоп!\n" +
                        "Чтобы использовать наш сервис, " +
                        "Вам необходимо пройти простую регистрацию. " +
                        "Введите ваш номер телефона (в международном формате +998** *******) " +
                        "или нажмите кнопку\n\"\uD83D\uDCF1 Отправить номер\".");
                sendMessage.setReplyMarkup(sendNumber());
                level = 1;
                break;
            case 1:
                phoneNumber = text;
                if (phoneNumber.startsWith("+998") || phoneNumber.startsWith("998")) {
                    sendMessage.setText("✅ Активировано успешно!\n" +
                            "Выберите раздел из меню!");
                    sendMessage.setReplyMarkup(menu());
                } else {
                    sendMessage.setText("Вы ввели номер телефона в неправильном формате,\n" +
                            "попробуйте еще раз или воспользуйтесь кнопкой ниже!");
                    sendMessage.setReplyMarkup(sendNumber());
                }
                level = 2;
                break;
            case 2:
                if (text.equalsIgnoreCase("✅Записаться")) {
                    sendMessage.setText("Пожалуйста, выберите одну из наших услуг!");
                    sendMessage.setReplyMarkup(selectService());
                    level = 4;
                } else if (text.equalsIgnoreCase("ℹПрайс-лист")) {
                    sendMessage.setText("Мужская стрижка -> до 180.000 so'm\n\n" +
                            "Мужская стрижка под машинку -> до 110.000 so'm\n\n" +
                            "Мужская стрижка и моделирование бороды -> до 250.000 so'm\n\n" +
                            "Моделирование бороды и усов -> до 100.000 so'm\n\n" +
                            "Мужская стрижка и королевское бритьё -> до 250.000 so'm\n\n" +
                            "Королевское бритьё -> до 120.000 so'm\n\n" +
                            "Бритьё головы опасной бритвой -> до 120.000 so'm\n\n" +
                            "Детская стрижка до 9 лет -> до 140.000 so'm\n\n" +
                            "Окантовка -> 70.000 so'm\n\n" +
                            "Стрижка отец и сын -> 280.000 so'm\n\n" +
                            "Камуфляж седины -> 140.000 so'm\n\n" +
                            "Чистка лица *черная маска * -> 120.000so'm");
                    sendMessage.setReplyMarkup(back());
                    level = 3;
                } else if (text.equalsIgnoreCase("\uD83D\uDDFAКак нас найти")) {
                    SendLocation sendLocation = new SendLocation();
                    sendLocation.setChatId(chatId);
                    sendLocation.setReplyMarkup(back());
                    sendLocation.setLatitude(41.306242);
                    sendLocation.setLongitude(69.265646);
                    try {
                        execute(sendLocation);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    level = 3;
                } else if (text.equalsIgnoreCase("Связаться с администратором ➡")) {
                    sendMessage.setText("@JAMAJURAKULOV");
                }
                break;
            case 3:
                if (text.equalsIgnoreCase("Назад ⬅️")) {
                    sendMessage.setText("Выберите раздел из меню!");
                    sendMessage.setReplyMarkup(menu());
                }
                level = 2;
                break;
            case 4:
                service = data;
//                sendMessage.setText("Выбрат дату");
//                sendMessage.setReplyMarkup(selectDay(service));
                editMessageText.setText("Выбрат дату");
                editMessageText.setMessageId(messageId);
                editMessageText.setInlineMessageId(inlineMessageId);
                editMarkup.setMessageId(messageId);
                editMarkup.setInlineMessageId(inlineMessageId);
                editMarkup.setReplyMarkup(selectDay(service));
                try {
                    execute(editMessageText);
                    execute(editMarkup);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                level = 5;
                break;
            case 5:
                date = data;
                editMessageText.setText("Выбрат время");
                editMessageText.setMessageId(messageId);
                editMessageText.setInlineMessageId(inlineMessageId);
                editMarkup.setMessageId(messageId);
                editMarkup.setInlineMessageId(inlineMessageId);
                editMarkup.setReplyMarkup(selectHours(service, data));
                try {
                    execute(editMessageText);
                    execute(editMarkup);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
//                sendMessage.setText("Выбрат время");
//                sendMessage.setReplyMarkup(selectHours(service, data));
                level = 6;
                break;
            case 6:
                hour = data;
                boolean isRegistered = altegio.bookOrder(name, phoneNum, "user@gamil.com", "Order by bot", service, date + "T" + hour);
                DeleteMessage deleteMessage = new DeleteMessage();
                deleteMessage.setChatId(chatId);
                deleteMessage.setMessageId(messageId);
                try {
                    execute(deleteMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                if (isRegistered) {
                    sendMessage.setText("✅Вы успешно зарегистрировались " + date + " числа в " + hour + " \uD83C\uDF89\uD83C\uDF89");
                } else {
                    sendMessage.setText("❌Извините, произошла ошибка, попробуйте позже!");
                }
                sendMessage.setReplyMarkup(back());
                level = 3;
                break;
        }

        try {
            execute(sendMessage);
        } catch (
                TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    public static ReplyKeyboardMarkup menu() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton button1 = new KeyboardButton("✅Записаться");
        row1.add(button1);

        KeyboardRow row2 = new KeyboardRow();
        KeyboardButton button2 = new KeyboardButton("ℹПрайс-лист");
        row2.add(button2);

        KeyboardRow row3 = new KeyboardRow();
        KeyboardButton button3 = new KeyboardButton("\uD83D\uDDFAКак нас найти");
        row3.add(button3);

        keyboard.add(row1);
        keyboard.add(row2);
        keyboard.add(row3);

        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup sendNumber() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton("\uD83D\uDCF1 Отправить номер");
        button.setRequestContact(true);
        row.add(button);
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static ReplyKeyboardMarkup back() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();
        KeyboardButton button1 = new KeyboardButton("Назад ⬅️");
        row1.add(button1);
        keyboard.add(row1);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public static InlineKeyboardMarkup selectHours(String service, String date) {
        Hours[] hours = altegio.getHours(service, date);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (int i = 0; i < hours.length; i += 2) {
            if (i % 2 == 0) {
                List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
                InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                inlineKeyboardButton1.setText(hours[i].getTime());
                inlineKeyboardButton1.setCallbackData(hours[i].getTime());
                keyboardButtonsRow1.add(inlineKeyboardButton1);
                if (i + 1 <= hours.length - 1) {
                    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
                    inlineKeyboardButton2.setText(hours[i + 1].getTime());
                    inlineKeyboardButton2.setCallbackData(hours[i + 1].getTime());
                    keyboardButtonsRow1.add(inlineKeyboardButton2);
                }
                rowList.add(keyboardButtonsRow1);
            }
        }
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup selectService() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText("Мужская стрижка");
        inlineKeyboardButton1.setCallbackData("10288893");
        keyboardButtonsRow1.add(inlineKeyboardButton1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText("Мужская стрижка под машинку");
        inlineKeyboardButton2.setCallbackData("10294221");
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText("Мужская стрижка и моделирование бороды");
        inlineKeyboardButton3.setCallbackData("10295306");
        keyboardButtonsRow3.add(inlineKeyboardButton3);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText("Моделирование бороды и усов");
        inlineKeyboardButton4.setCallbackData("10295310");
        keyboardButtonsRow4.add(inlineKeyboardButton4);

        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText("Мужская стрижка и королевское бритьё");
        inlineKeyboardButton5.setCallbackData("10295313");
        keyboardButtonsRow5.add(inlineKeyboardButton5);

        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
        inlineKeyboardButton6.setText("Королевское бритьё");
        inlineKeyboardButton6.setCallbackData("10295318");
        keyboardButtonsRow6.add(inlineKeyboardButton6);

        List<InlineKeyboardButton> keyboardButtonsRow7 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();
        inlineKeyboardButton7.setText("Бритьё головы опасной бритвой");
        inlineKeyboardButton7.setCallbackData("10295320");
        keyboardButtonsRow7.add(inlineKeyboardButton7);

        List<InlineKeyboardButton> keyboardButtonsRow8 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton8 = new InlineKeyboardButton();
        inlineKeyboardButton8.setText("Детская стрижка до 9 лет");
        inlineKeyboardButton8.setCallbackData("10295322");
        keyboardButtonsRow8.add(inlineKeyboardButton8);

        List<InlineKeyboardButton> keyboardButtonsRow9 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton9 = new InlineKeyboardButton();
        inlineKeyboardButton9.setText("Окантовка");
        inlineKeyboardButton9.setCallbackData("10295326");
        keyboardButtonsRow9.add(inlineKeyboardButton9);

        List<InlineKeyboardButton> keyboardButtonsRow10 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton10 = new InlineKeyboardButton();
        inlineKeyboardButton10.setText("Стрижка отец и сын");
        inlineKeyboardButton10.setCallbackData("10295332");
        keyboardButtonsRow10.add(inlineKeyboardButton10);

        List<InlineKeyboardButton> keyboardButtonsRow11 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton11 = new InlineKeyboardButton();
        inlineKeyboardButton11.setText("Камуфляж седины");
        inlineKeyboardButton11.setCallbackData("10295335");
        keyboardButtonsRow11.add(inlineKeyboardButton11);

        List<InlineKeyboardButton> keyboardButtonsRow12 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton12 = new InlineKeyboardButton();
        inlineKeyboardButton12.setText("Чистка лица *черная маска *");
        inlineKeyboardButton12.setCallbackData("10295338");
        keyboardButtonsRow12.add(inlineKeyboardButton12);

//        List<InlineKeyboardButton> keyboardButtonsRow13 = new ArrayList<>();
//        InlineKeyboardButton inlineKeyboardButton13 = new InlineKeyboardButton();
//        inlineKeyboardButton13.setText("Next");
//        inlineKeyboardButton13.setCallbackData("next");
//        keyboardButtonsRow13.add(inlineKeyboardButton13);

        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);
        rowList.add(keyboardButtonsRow6);
        rowList.add(keyboardButtonsRow7);
        rowList.add(keyboardButtonsRow8);
        rowList.add(keyboardButtonsRow9);
        rowList.add(keyboardButtonsRow10);
        rowList.add(keyboardButtonsRow11);
        rowList.add(keyboardButtonsRow12);
//        rowList.add(keyboardButtonsRow13);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup selectDay(String service) {
        Days days = altegio.getDays(service);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setText(days.getBooking_dates().get(0));
        inlineKeyboardButton1.setCallbackData(days.getBooking_dates().get(0));
        keyboardButtonsRow1.add(inlineKeyboardButton1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        inlineKeyboardButton2.setText(days.getBooking_dates().get(1));
        inlineKeyboardButton2.setCallbackData(days.getBooking_dates().get(1));
        keyboardButtonsRow2.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        inlineKeyboardButton3.setText(days.getBooking_dates().get(2));
        inlineKeyboardButton3.setCallbackData(days.getBooking_dates().get(2));
        keyboardButtonsRow3.add(inlineKeyboardButton3);

        List<InlineKeyboardButton> keyboardButtonsRow4 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
        inlineKeyboardButton4.setText(days.getBooking_dates().get(3));
        inlineKeyboardButton4.setCallbackData(days.getBooking_dates().get(3));
        keyboardButtonsRow4.add(inlineKeyboardButton4);

        List<InlineKeyboardButton> keyboardButtonsRow5 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
        inlineKeyboardButton5.setText(days.getBooking_dates().get(4));
        inlineKeyboardButton5.setCallbackData(days.getBooking_dates().get(4));
        keyboardButtonsRow5.add(inlineKeyboardButton5);

        List<InlineKeyboardButton> keyboardButtonsRow6 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
        inlineKeyboardButton6.setText(days.getBooking_dates().get(5));
        inlineKeyboardButton6.setCallbackData(days.getBooking_dates().get(5));
        keyboardButtonsRow6.add(inlineKeyboardButton6);

        List<InlineKeyboardButton> keyboardButtonsRow7 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton7 = new InlineKeyboardButton();
        inlineKeyboardButton7.setText(days.getBooking_dates().get(6));
        inlineKeyboardButton7.setCallbackData(days.getBooking_dates().get(6));
        keyboardButtonsRow7.add(inlineKeyboardButton7);

        List<InlineKeyboardButton> keyboardButtonsRow8 = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton8 = new InlineKeyboardButton();
        inlineKeyboardButton8.setText(days.getBooking_dates().get(7));
        inlineKeyboardButton8.setCallbackData(days.getBooking_dates().get(7));
        keyboardButtonsRow8.add(inlineKeyboardButton8);

        rowList.add(keyboardButtonsRow1);
        rowList.add(keyboardButtonsRow2);
        rowList.add(keyboardButtonsRow3);
        rowList.add(keyboardButtonsRow4);
        rowList.add(keyboardButtonsRow5);
        rowList.add(keyboardButtonsRow6);
        rowList.add(keyboardButtonsRow7);
        rowList.add(keyboardButtonsRow8);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
