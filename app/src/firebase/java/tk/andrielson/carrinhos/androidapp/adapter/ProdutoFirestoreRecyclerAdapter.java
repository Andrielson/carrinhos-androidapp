package tk.andrielson.carrinhos.androidapp.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import tk.andrielson.carrinhos.androidapp.data.model.ProdutoImpl;
import tk.andrielson.carrinhos.androidapp.databinding.FragmentProdutoBinding;
import tk.andrielson.carrinhos.androidapp.ui.fragment.ProdutoFragment;
import tk.andrielson.carrinhos.androidapp.ui.viewholder.ProdutoViewHolder;

/**
 * Created by Andrielson on 04/03/2018.
 */

public class ProdutoFirestoreRecyclerAdapter extends FirestoreRecyclerAdapter<ProdutoImpl, ProdutoViewHolder> {

    private static final String TAG = ProdutoFirestoreRecyclerAdapter.class.getSimpleName();

    private final ProdutoFragment.OnListFragmentInteractionListener mListener;

    public ProdutoFirestoreRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ProdutoImpl> options, ProdutoFragment.OnListFragmentInteractionListener listener) {
        super(options);
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProdutoViewHolder holder, int position, @NonNull ProdutoImpl model) {
        holder.bind(model, this.mListener);
    }

    @Override
    @NonNull
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup group, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(group.getContext());
        FragmentProdutoBinding binding = FragmentProdutoBinding.inflate(layoutInflater, group, false);
        return new ProdutoViewHolder(binding);
    }
}