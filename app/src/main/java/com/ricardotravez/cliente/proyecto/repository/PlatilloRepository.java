package com.ricardotravez.cliente.proyecto.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ricardotravez.cliente.proyecto.api.ConfigApi;
import com.ricardotravez.cliente.proyecto.api.PlatilloApi;
import com.ricardotravez.cliente.proyecto.entity.GenericResponse;
import com.ricardotravez.cliente.proyecto.entity.service.Platillo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlatilloRepository {
    private final PlatilloApi api;
    private static PlatilloRepository repository;

    public PlatilloRepository() {
        this.api = ConfigApi.getPlatilloApi();
    }

    public static PlatilloRepository getInstance() {
        if (repository == null) {
            repository = new PlatilloRepository();
        }
        return repository;
    }

    public LiveData<GenericResponse<List<Platillo>>> listarPlatillosRecomendados(){
        final MutableLiveData<GenericResponse<List<Platillo>>> mld = new MutableLiveData<>();
        this.api.listarPlatillosRecomendados().enqueue(new Callback<GenericResponse<List<Platillo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Platillo>>> call, Response<GenericResponse<List<Platillo>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Platillo>>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }

    public LiveData<GenericResponse<List<Platillo>>> listarPlatillosPorCategoria(int idC){
        final MutableLiveData<GenericResponse<List<Platillo>>> mld = new MutableLiveData<>();
        this.api.listarPlatillosPorCategoria(idC).enqueue(new Callback<GenericResponse<List<Platillo>>>() {
            @Override
            public void onResponse(Call<GenericResponse<List<Platillo>>> call, Response<GenericResponse<List<Platillo>>> response) {
                mld.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GenericResponse<List<Platillo>>> call, Throwable t) {
                mld.setValue(new GenericResponse<>());
                t.printStackTrace();
            }
        });
        return mld;
    }
}
