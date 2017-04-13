package app.devmedia.com.br.appdevmedia.acync;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import app.devmedia.com.br.appdevmedia.MainActivity;
import app.devmedia.com.br.appdevmedia.util.Util;
import app.devmedia.com.br.appdevmedia.validation.LoginValidation;

/**
 * Created by Wanderson Hipolito on 20/03/2017.
 */

public class AcyncUser extends AsyncTask<String, String, String> {

    //informando a activity que vai ser passada os parametros
    private LoginValidation loginValidation;
    private Activity activity;

    //criando um metodo publico no qual a activity vai ser usada
    public AcyncUser(LoginValidation loginValidation) {
        this.loginValidation = loginValidation;
        this.activity = loginValidation.getActivity();
    }

    @Override
    protected String doInBackground(String... url) {
        //StringBuilder vai ser iniciado como vazio para nao estourar nenhuma exeção
        StringBuilder resultado = new StringBuilder("");
        try {
            // fazendo a conexao com a URL
            String path = url[0];
            path += "?usuario=" + loginValidation.getLogin() + "&senha=" + loginValidation.getSenha();

            URL urlNet = new URL(path);
            HttpURLConnection con = (HttpURLConnection) urlNet.openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.connect();

            //LENDO o que esta sendo enviado/escrito
            InputStream ipn = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ipn));
            String linha = "";

            //CRIANDO UM LAÇO PARA QUE SE POSSA SER O QUE VAI SER IMPRESSO
            while ((linha = bufferedReader.readLine()) != null) {
                resultado.append(linha);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //retornando o resultado
        return resultado.toString();
    }


    @Override
    protected void onPostExecute(String resultado) {

        if (Boolean.valueOf(resultado)){
            SharedPreferences.Editor editor = activity.getSharedPreferences("pref", Context.MODE_PRIVATE).edit();
            editor.putString("login", loginValidation.getLogin());
            editor.putString("senha", loginValidation.getSenha());
            editor.commit();

            Intent goMain = new Intent(activity, MainActivity.class);
            activity.startActivity(goMain);
            activity.finish();


        } else{
            Util.showMsgToast(activity, "Login/Senha inválidos!");
        }
    }
}
