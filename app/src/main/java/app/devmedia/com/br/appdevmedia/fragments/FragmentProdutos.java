package app.devmedia.com.br.appdevmedia.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import app.devmedia.com.br.appdevmedia.R;

/**
 * Created by Wanderson Hipolito on 19/03/2017.
 */

public class FragmentProdutos extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_produtos, container, false);

        ImageView imgProdutos = (ImageView) viewRoot.findViewById(R.id.imgProdutos);
        Picasso.with(viewRoot.getContext()).load(R.drawable.produto_um).into(imgProdutos);
        Picasso.with(viewRoot.getContext()).load(R.drawable.produto_dois).into(imgProdutos);

        return viewRoot;
    }
}
