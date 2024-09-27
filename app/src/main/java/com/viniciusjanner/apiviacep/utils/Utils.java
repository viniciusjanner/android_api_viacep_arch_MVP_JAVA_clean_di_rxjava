package com.viniciusjanner.apiviacep.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viniciusjanner.apiviacep.CepApp;
import com.viniciusjanner.apiviacep.R;

public class Utils {

    private static final String CEP_PATTERN = "\\d{8}";

    public static boolean isValidCep(String cep) {
        return cep != null && cep.matches(CEP_PATTERN); // Verifica se o CEP tem 8 dígitos e contém apenas números
    }

    public static String safeValue(String value) {
        return value != null ? value : "";
    }

    public static void openKeyboard(View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void shareText(String textToShare, Context context) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);

        if (shareIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(
                Intent.createChooser(shareIntent, "Compartilhar via")
            );
        }
    }

    public static void copyTextToClipboard(String textToCopy) {
        Context context = CepApp.getAppContext();

        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Texto copiado", textToCopy);
        clipboard.setPrimaryClip(clip);

        String message = "Texto copiado para a área de transferência";
        Utils.showCustomToast(message);
    }

    private static Toast currentToast;

    public static void showCustomToast(String message) {
        if (currentToast != null) {
            currentToast.cancel(); // Cancela o Toast anterior se existir
        }

        Context context = CepApp.getAppContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, new FrameLayout(context), false);

        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        currentToast = toast; // Atualiza a referência para o Toast atual
    }

}
