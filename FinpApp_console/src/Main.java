import repositorios.CategoriaRepository;
import repositorios.MovimientoRepository;
import repositorios.PresupuestoRepository;
import repositorios.memoria.CategoriaRepositoryInMemory;
import repositorios.memoria.MovimientoRepositoryInMemory;
import repositorios.memoria.PresupuestoRepositoryInMemory;
import servicios.FinanzasService;
import ui.MenuConsola;

public class Main {
    public static void main(String[] args) {
        CategoriaRepository categoriaRepo = new CategoriaRepositoryInMemory();
        MovimientoRepository movimientoRepo = new MovimientoRepositoryInMemory();
        PresupuestoRepository presupuestoRepo = new PresupuestoRepositoryInMemory();

        FinanzasService service = new FinanzasService(categoriaRepo, movimientoRepo, presupuestoRepo);
        MenuConsola menu = new MenuConsola(service);
        menu.iniciar();
    }
}
