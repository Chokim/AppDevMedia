package app.devmedia.com.br.appdevmedia.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Wanderson Hipolito on 13/04/2017.
 */

public class ProdutoRecycleAdapter extends RecyclerView.Adapter<ProdutoRecycleAdapter.ViewHolder> {



    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public ProdutoRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ProdutoRecycleAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
