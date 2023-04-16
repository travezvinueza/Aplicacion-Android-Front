package com.ricardotravez.cliente.proyecto.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ricardotravez.cliente.proyecto.entity.GenericResponse;
import com.ricardotravez.cliente.proyecto.entity.service.Cliente;
import com.ricardotravez.cliente.proyecto.repository.ClienteRepository;

public class ClienteViewModel extends AndroidViewModel {
    private final ClienteRepository repository;

    public ClienteViewModel(@NonNull Application application) {
        super(application);
        this.repository = ClienteRepository.getInstance();
    }

    public LiveData<GenericResponse<Cliente>> guardarCliente(Cliente c){
        return  repository.guardarCliente(c);
    }
}
