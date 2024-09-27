package com.viniciusjanner.apiviacep.view;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.viniciusjanner.apiviacep.databinding.ActivityMainBinding;
import com.viniciusjanner.apiviacep.model.Address;
import com.viniciusjanner.apiviacep.presenter.CepContract;
import com.viniciusjanner.apiviacep.presenter.CepFactory;
import com.viniciusjanner.apiviacep.utils.Utils;

public class MainActivity extends AppCompatActivity implements CepContract.View {

    private ActivityMainBinding binding;
    private CepContract.Presenter presenter;

    private static final int FLIPPER_CHILD_MESSAGE = 0;
    private static final int FLIPPER_CHILD_SUCCESS = 1;
    private static final int FLIPPER_CHILD_LOADING = 2;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initPresenter();
        setupListeners();
        Utils.openKeyboard(binding.edtCep);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
    }

    private void initPresenter() {
        presenter = CepFactory.createCepPresenter();
    }

    private void setupListeners() {
        binding.btnBuscar.setOnClickListener(v -> fetchAddress());

        binding.edtCep.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                fetchAddress();
                return true;
            }
            return false;
        });

        binding.includeContentResult.iconShare.setOnClickListener(v -> presenter.shareAddress());

        binding.includeContentResult.iconCopy.setOnClickListener(v -> presenter.copyAddress());
    }

    private String getInputCep() {
        return binding.edtCep.getText().toString().trim();
    }

    private void fetchAddress() {
        Utils.closeKeyboard(binding.edtCep);
        presenter.fetchAddress(getInputCep());
    }

    @Override
    public void showLoading() {
        binding.btnBuscar.setEnabled(false);
        binding.viewFlipper.setDisplayedChild(FLIPPER_CHILD_LOADING);
    }

    @Override
    public void hideLoading() {
        binding.btnBuscar.setEnabled(true);
    }

    @Override
    public void displayAddress(Address address) {
        binding.viewFlipper.setDisplayedChild(FLIPPER_CHILD_SUCCESS);
        binding.includeContentResult.txtResult.setText(
            address.formatAddress()
        );
    }

    @Override
    public void displayError(String message) {
        binding.viewFlipper.setDisplayedChild(FLIPPER_CHILD_MESSAGE);
        binding.messageResult.setText(message);
    }
}
