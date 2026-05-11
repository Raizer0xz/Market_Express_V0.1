package com.example.MS_productos.service;


import com.example.MS_productos.model.Producto;
import com.example.MS_productos.repository.CategoriaRepository;
import com.example.MS_productos.repository.PrecioProductoRepository;
import com.example.MS_productos.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
public class ProductoService {
    @Autowired
    private PrecioProductoRepository precioProductoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Producto> listarProductos(){
        log.info("Listado de todos los productos activos");

        return productoRepository.findByActivoTrue();
    }

    public Producto obternerPorId(Long id){
        return productoRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Producto no encontrado:" + id));

    }
    public List<Producto> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaIdAndActivoTrue(categoriaId);
    }

    public Producto crearProducto(Producto producto){
        categoriaRepository.findById(producto.getCategoria().getId()).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        log.info("Creando producto: {}", producto.getNombre());
        return productoRepository.save(producto);

    }
    public void eliminarProducto(Long id) {
        Producto p = obternerPorId(id);
        // Primero eliminar los precios asociados
        precioProductoRepository.deleteAll(precioProductoRepository.findByProductoId(id));
        // Luego eliminar el producto físicamente
        productoRepository.delete(p);
        log.info("Producto {} eliminado", id);
    }




}
