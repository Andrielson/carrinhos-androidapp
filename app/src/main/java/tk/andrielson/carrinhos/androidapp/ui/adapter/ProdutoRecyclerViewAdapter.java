package tk.andrielson.carrinhos.androidapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import tk.andrielson.carrinhos.androidapp.data.model.Produto;
import tk.andrielson.carrinhos.androidapp.databinding.FragmentProdutoListaItemBinding;
import tk.andrielson.carrinhos.androidapp.ui.fragment.ListaProdutoFragment;
import tk.andrielson.carrinhos.androidapp.ui.viewhandler.ListaProdutoItemHandler;

/**
 * Created by Andrielson on 06/03/2018.
 */
//TODO: estudar possibilidade de se tornar uma classe abstrata para todas as entidades
public class ProdutoRecyclerViewAdapter extends RecyclerView.Adapter<ProdutoRecyclerViewAdapter.ProdutoViewHolder> {
    protected final ListaProdutoFragment.OnListFragmentInteractionListener mListener;

    protected List<? extends Produto> listaProduto;

    public ProdutoRecyclerViewAdapter(ListaProdutoFragment.OnListFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    @Override
    @NonNull
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(group.getContext());
        FragmentProdutoListaItemBinding binding = FragmentProdutoListaItemBinding.inflate(layoutInflater, group, false);
        return new ProdutoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        holder.bind(listaProduto.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return (listaProduto != null) ? listaProduto.size() : 0;
    }

    public void setListaProduto(final List<? extends Produto> novaLista) {
        if (this.listaProduto == null) {
            this.listaProduto = novaLista;
            notifyItemRangeInserted(0, novaLista.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffCallback(novaLista, this.listaProduto));
            listaProduto = novaLista;
            result.dispatchUpdatesTo(this);
        }
    }

    private static final class DiffCallback extends DiffUtil.Callback {
        private final List<? extends Produto> listaNova;
        private final List<? extends Produto> listaAntiga;

        DiffCallback(List<? extends Produto> listaNova, List<? extends Produto> listaAntiga) {
            this.listaNova = listaNova;
            this.listaAntiga = listaAntiga;
        }

        @Override
        public int getOldListSize() {
            return listaAntiga.size();
        }

        @Override
        public int getNewListSize() {
            return listaNova.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return Objects.equals(listaAntiga.get(oldItemPosition).getCodigo(), listaNova.get(newItemPosition).getCodigo());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            Produto p1 = listaAntiga.get(oldItemPosition);
            Produto p2 = listaNova.get(newItemPosition);
            return p1.hashCode() == p2.hashCode();
        }
    }
    
    public class ProdutoViewHolder extends RecyclerView.ViewHolder {

        private final String TAG = ProdutoViewHolder.class.getSimpleName();
        private final FragmentProdutoListaItemBinding binding;

        public ProdutoViewHolder(FragmentProdutoListaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final Produto produto, final ListaProdutoFragment.OnListFragmentInteractionListener listener) {
            binding.setProduto(produto);
            binding.setHandler(new ListaProdutoItemHandler(listener, produto));
            binding.executePendingBindings();
        }

    }
}
