package repositorios.memoria;

import dominio.CategoriaGasto;
import repositorios.CategoriaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaRepositoryInMemory implements CategoriaRepository {
    private final List<CategoriaGasto> categorias = new ArrayList<>();
    private long secuenciaId = 1L;

    @Override
    public CategoriaGasto guardar(CategoriaGasto categoria) {
        if (categoria.getId() == null) {
            categoria.setId(secuenciaId++);
            categorias.add(categoria);
        } else {
            categorias.removeIf(c -> c.getId().equals(categoria.getId()));
            categorias.add(categoria);
        }
        return categoria;
    }

    @Override
    public List<CategoriaGasto> obtenerTodas() {
        return new ArrayList<>(categorias);
    }

    @Override
    public Optional<CategoriaGasto> buscarPorId(Long id) {
        return categorias.stream().filter(c -> c.getId().equals(id)).findFirst();
    }
}

