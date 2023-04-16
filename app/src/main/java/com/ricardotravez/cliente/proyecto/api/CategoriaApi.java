package com.ricardotravez.cliente.proyecto.api;

import com.ricardotravez.cliente.proyecto.entity.GenericResponse;
import com.ricardotravez.cliente.proyecto.entity.service.Categoria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaApi {
    String base = "api/categoria";

    @GET(base)
    Call<GenericResponse<List<Categoria>>> listarCategoriasActivas();
}
