package com.petinder.petinder.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.petinder.petinder.R;
import com.petinder.petinder.activity.CadastroUsuarioActivity;

public class LoginFragment extends Fragment implements View.OnClickListener{
    private Button btnEntrar;
    private Button btnCadastrar;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_login, container, false);
        // Inflate the layout for this fragment

        btnCadastrar= (Button) fragment.findViewById(R.id.btnCadastrar);
        btnEntrar= (Button) fragment.findViewById(R.id.btnEntrar);
        btnCadastrar.setOnClickListener(this);
        btnEntrar.setOnClickListener(this);
        return fragment;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEntrar:
/*
                if (validarCampos()) {
                    SharedPreferences prefs = getActivity().getSharedPreferences("Configuracoes", Context.MODE_PRIVATE);

                    LoginJson loginJson = new LoginJson();
                    String data = loginJson.loginToJson(etEmail.getText().toString().toLowerCase(),
                            etSenha.getText().toString(), prefs.getString("gcmId", ""));
                    LoginTask task = new LoginTask(data, (LoginActivity) getActivity());
                    task.execute();
                }
                break;
            case R.id.esqueciSenha:
                ((LoginActivity) getActivity()).RecuperarSenha();
                break;

*/
            case R.id.btnCadastrar:
                Intent intent = new Intent(getActivity(), CadastroUsuarioActivity.class);
                startActivity(intent);
        }

    }

}
