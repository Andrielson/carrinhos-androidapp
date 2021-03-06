package tk.andrielson.carrinhos.androidapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tk.andrielson.carrinhos.androidapp.DI;
import tk.andrielson.carrinhos.androidapp.data.dao.VendedorDao;
import tk.andrielson.carrinhos.androidapp.data.model.Vendedor;
import tk.andrielson.carrinhos.androidapp.observable.VendedorObservable;

/**
 * Created by anfesilva on 07/03/2018.
 */

public class ListaVendedorViewModel extends ViewModel {
    private final MediatorLiveData<List<Vendedor>> mediatorLiveDataListaVendedores;

    public ListaVendedorViewModel() {
        mediatorLiveDataListaVendedores = new MediatorLiveData<>();
        // Set por padrão null, até carregar os dados do repositório
        mediatorLiveDataListaVendedores.setValue(null);

        VendedorDao vendedorDao = DI.newVendedorDao();
        //noinspection unchecked
        LiveData<List<Vendedor>> vendedores = vendedorDao.getAll();
        mediatorLiveDataListaVendedores.addSource(vendedores, mediatorLiveDataListaVendedores::setValue);
    }

    public LiveData<List<VendedorObservable>> getVendedores() {
        return Transformations.map(mediatorLiveDataListaVendedores, input -> {
            List<VendedorObservable> lista = new ArrayList<>();
            if (input != null)
                for (Vendedor p : input)
                    lista.add(new VendedorObservable(p));
            return lista;
        });
    }
}
