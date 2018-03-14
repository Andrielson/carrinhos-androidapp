package tk.andrielson.carrinhos.androidapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import tk.andrielson.carrinhos.androidapp.R;
import tk.andrielson.carrinhos.androidapp.observable.ProdutoObservable;
import tk.andrielson.carrinhos.androidapp.ui.fragment.CadastroProdutoFragment;

public class ProdutoActivity extends AppCompatActivity implements CadastroProdutoFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        Intent intent = getIntent();
        Long codigoProduto = (intent != null && intent.hasExtra("produtoCodigo")) ? intent.getLongExtra("produtoCodigo", 0) : null;
        Fragment fragment = CadastroProdutoFragment.newInstance(codigoProduto);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_holder, fragment);
        ft.commit();
    }

    @Override
    public void salvarProduto(ProdutoObservable produto, boolean insercao) {
        Toast.makeText(this, "Produto " + (insercao ? "adicionado!" : "atualizado!"), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void excluirProduto(ProdutoObservable produto) {
        finish();
    }
}
