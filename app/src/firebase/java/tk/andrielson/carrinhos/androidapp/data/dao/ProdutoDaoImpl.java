package tk.andrielson.carrinhos.androidapp.data.dao;

import android.annotation.SuppressLint;
import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import tk.andrielson.carrinhos.androidapp.data.model.Produto;
import tk.andrielson.carrinhos.androidapp.data.model.ProdutoImpl;

/**
 * The type Produto dao.
 */
@SuppressLint("DefaultLocale")
public final class ProdutoDaoImpl extends FirestoreDao implements ProdutoDao {

    private static final String COLECAO = ProdutoImpl.COLLECTION;
    private static final String TAG = ProdutoDaoImpl.class.getSimpleName();
    private final Query queryPadrao;

    public ProdutoDaoImpl() {
        collection = db.collection(COLECAO);
        queryPadrao = collection.whereEqualTo(ProdutoImpl.ATIVO, true);
    }

    public void testaTeste() {
        Log.d(TAG, getColecaoID(COLECAO));
        Log.d(TAG, getColecaoID("vendedores"));
    }

    /**
     * Insere um produto no banco de dados e retorna o código gerado.
     *
     * @param produto o produto a ser inserido
     * @return o código gerado para o produto
     */
    @Override
    public long insert(Produto produto) {
        String ultimoID = getColecaoID(COLECAO);
        Long novoCodigo = Long.valueOf(ultimoID) + 1;
        produto.setCodigo(novoCodigo);
        String id = String.format("%020d", novoCodigo);
        collection.document(id).set(produto);
        return novoCodigo;
    }

    /**
     * Atualiza as informações de um produto já existente no banco de dados.
     *
     * @param produto o produto a ser atualizado
     * @return o número de produtos atualizados
     */
    @Override
    public int update(Produto produto) {
        return 0;
    }

    /**
     * Remove um produto do banco de dados
     *
     * @param produto o produto a ser removido
     * @return o número de produto removidos
     */
    @Override
    public int delete(Produto produto) {
        return 0;
    }

    /**
     * Procura um produto especificado pelo código informado e o retorna encapsulado
     * numa LiveData para sempre manter a informação atualizada.
     *
     * @param codigo o código do produto a ser procurado/retornado
     * @return o produto encapsulado em uma LiveData
     */
    @Override
    public LiveData<Produto> getByCodigo(final Long codigo) {
        Query query = queryPadrao.whereEqualTo("codigo", codigo);
        FirestoreQueryLiveData liveData = new FirestoreQueryLiveData(query);
        return Transformations.map(liveData, new ProdutoDeserializer());
    }

    /**
     * Remove todos os produtos do banco de dados
     */
    @Override
    public void deleteAll() {

    }

    /**
     * Consulta todos os produtos do banco de dados e retorna uma lista
     * encapsulada numa LiveData observável, para manter a lista sempre atualizada.
     *
     * @return a lista de produtos encapsulada em uma LiveData
     */
    @Override
    public LiveData<List<Produto>> getAll() {
        FirestoreQueryLiveData liveData = new FirestoreQueryLiveData(queryPadrao);
        return Transformations.map(liveData, new ListaProdutoDeserializer());
    }

    private class ListaProdutoDeserializer implements Function<QuerySnapshot, List<Produto>> {

        @Override
        public List<Produto> apply(QuerySnapshot input) {
            List<Produto> lista = new ArrayList<>();
            for (DocumentSnapshot doc : input.getDocuments()) {
                lista.add(doc.toObject(ProdutoImpl.class));
            }
            return lista;
        }
    }

    private class ProdutoDeserializer implements Function<QuerySnapshot, Produto> {
        @Override
        public ProdutoImpl apply(QuerySnapshot input) {
            return input.getDocuments().get(0).toObject(ProdutoImpl.class);
        }
    }

}
