package app.devmedia.com.br.appdevmedia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.devmedia.com.br.appdevmedia.R;
import app.devmedia.com.br.appdevmedia.adapter.ProfissaoArrayAdapter;
import app.devmedia.com.br.appdevmedia.entity.Profissao;
import app.devmedia.com.br.appdevmedia.entity.User;
import app.devmedia.com.br.appdevmedia.util.Constantes;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import android.support.v7.app.*;
/**
 * Created by Wanderson Hipolito on 19/03/2017.
 */

public class FragmentPerfil extends Fragment {

    private Button btnCadastrar;
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtBio;
    private Spinner spnProfissao;
    private List<Profissao> profissaoList;
    private RadioGroup rbgSexo;
    private RadioButton rbtMasculino, rbtFeminino;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        final Gson gson = new Gson();

        rbgSexo = (RadioGroup) view.findViewById(R.id.rbgSexo);
        rbtFeminino = (RadioButton) view.findViewById(R.id.rbtFeminino);
        rbtMasculino = (RadioButton) view.findViewById(R.id.rbtMasculino);
        edtNome = (EditText) view.findViewById(R.id.edtNome);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtBio = (EditText) view.findViewById(R.id.edtBio);
        btnCadastrar = (Button) view.findViewById(R.id.btnCadastrar);
        spnProfissao = (Spinner) view.findViewById(R.id.spnProfissao);


        new AsyncHttpClient().get(Constantes.URL_WS_BASE + "user/get_profissoes", new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if (response != null) {
                    Type type = new TypeToken<List<Profissao>>() {}.getType();
                    profissaoList = gson.fromJson(response.toString(), type);
                    spnProfissao.setAdapter(new ProfissaoArrayAdapter(getActivity(), R.layout.linha_profissao, profissaoList));
                }else{
                    Toast.makeText(getActivity(), "Erro ao carregar lista de profissões", Toast.LENGTH_LONG).show();
                }
            }
        });

                //Referente a implementaÇÃO ANTIGA DO JSON
                //if (response != null) {
                   // for (int i = 0; i < response.length(); i++) {
                      //  try {
                          //  JSONObject jsonObject = response.getJSONObject(i);
                          //  profissaoList.add(new Gson().fromJson(jsonObject.toString(), Profissao.class));
                      //  } catch (JSONException e) {
                       //     e.printStackTrace();
                       // }
                  //  }
              //  }

                //spnProfissao.setAdapter(new ProfissaoArrayAdapter(getActivity(), R.layout.linha_profissao, profissaoList));
       //     }
        //});


        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!validarNome()) {
                    return;
                }

                String jason = gson.toJson(montarPessoa());

                try {
                   StringEntity stringEntity = new StringEntity(jason);
                    new AsyncHttpClient().post(null, Constantes.URL_WS_BASE + "user/add", stringEntity, "application/jason", new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            gson.fromJson(response.toString(), User.class);
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private boolean validarNome() {
        if (edtNome.getText().toString().trim().isEmpty()) {
            edtNome.setError("Campo nome é obrigatório");
            return false;
        }
        if (edtEmail.getText().toString().trim().isEmpty()) {
            edtEmail.setError("Campo email é obrigatório");
            return false;
        }
        if (edtBio.getText().toString().trim().isEmpty()) {
            edtBio.setError("Campo bio é obrigatório");
            return false;
        }
        return true;
    }

    private User montarPessoa() {

        User pessoa = new User();

        pessoa.setNome(edtNome.getText().toString());
        pessoa.setEmail(edtEmail.getText().toString());
        pessoa.setMiniBio(edtBio.getText().toString());
        pessoa.setSexo(rbtMasculino.isChecked() ? 'M' : 'F');
        pessoa.setProfissao((Profissao) spnProfissao.getSelectedItem());

        return pessoa;
    }
}
