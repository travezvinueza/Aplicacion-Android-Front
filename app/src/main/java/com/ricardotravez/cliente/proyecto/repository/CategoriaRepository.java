package com.ricardotravez.cliente.proyecto.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ricardotravez.cliente.proyecto.api.CategoriaApi;
import com.ricardotravez.cliente.proyecto.api.ConfigApi;
import com.ricardotravez.cliente.proyecto.entity.GenericResponse;
import com.ricardotravez.cliente.proyecto.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriaRepository {
    private final CategoriaApi api;
    private static CategoriaRepository repository;

    public CategoriaRepository() {
        this.api = ConfigApi.getCategoriaApi();
    }
    public static CategoriaRepository getInstance(){
        if(repository == null){
            repository = new CategoriaRepository();
        }
        return repository;
    }
    public LiveData<GenericResponse<List<Categoria>>> listarCategoriasActivas(){
        final MutableLiveData<GenericResponse<List<Categoria>>> mld = new MutableLiveData<>();
        this.api.listarCategoriasActivas().enqueue(new Callback<GenericResponse<List<Categoria>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Categoria>>> call, Response<GenericResponse<List<Categoria>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Categoria>>> call, Throwable t) {
                System.out.println("Error al obtener las categorías: " + t.getMessage());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
