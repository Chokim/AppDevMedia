package app.devmedia.com.br.appdevmedia.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import app.devmedia.com.br.appdevmedia.R;
import app.devmedia.com.br.appdevmedia.entity.Profissao;

/**
 * Created by Wanderson Hipolito on 30/03/2017.
 */

public class ProfissaoArrayAdapter extends ArrayAdapter<Profissao> {

    private List<Profissao> profissaoList;
    private Context context;

    public ProfissaoArrayAdapter(@NonNull Context context, @LayoutRes int resource, List<Profissao> profissaoList) {
        super(context, resource);
        this.profissaoList = profissaoList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return profissaoList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getViewAux(position, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getViewAux(position, parent);
    }

    @Nullable
    @Override
    public Profissao getItem(int position) {
        return profissaoList.get(position);
    }

    @NonNull
    private View getViewAux(int position, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View linha = layoutInflater.inflate(R.layout.linha_profissao, parent, false);
        TextView txtProfissao = (TextView) linha.findViewById(R.id.txtProfissao);
        txtProfissao.setText(profissaoList.get(position).getDescProfissao());
        return linha;
    }


}
