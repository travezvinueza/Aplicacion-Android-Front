package com.ricardotravez.cliente.proyecto.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ricardotravez.cliente.proyecto.entity.GenericResponse;
import com.ricardotravez.cliente.proyecto.entity.service.Categoria;
import com.ricardotravez.cliente.proyecto.repository.CategoriaRepository;

import java.util.List;

public class CategoriaViewModel extends AndroidViewModel {
    private final CategoriaRepository repository;


    public CategoriaViewModel(@NonNull Application application) {
        super(application);
        this.repository = CategoriaRepository.getInstance();
    }

    public LiveData<GenericResponse<List<Categoria>>> listarCategoriasActivas(){
        return this.repository.listarCategoriasActivas();
    }
}
