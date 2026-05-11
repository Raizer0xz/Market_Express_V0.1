package com.example.MS_productos.service;

import com.example.MS_productos.model.Categoria;
import com.example.MS_productos.repository.CategoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategorias() {
        log.info("Listando todas las categorías");
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
    }

    public Categoria crearCategoria(Categoria categoria) {
        log.info("Creando categoría: {}", categoria.getNombre());
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
        try {
            categoriaRepository.deleteById(id);
            log.info("Categoría {} eliminada", id);
        } catch (Exception e) {
            throw new RuntimeException(
                    "No se puede eliminar la categoría porque tiene productos asociados. " +
                            "Elimina primero los productos."
            );
        }
    }
}