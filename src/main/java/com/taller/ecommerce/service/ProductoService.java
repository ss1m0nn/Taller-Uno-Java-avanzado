package com.taller.ecommerce.service;

import com.taller.ecommerce.dto.ProductoRequestDTO;
import com.taller.ecommerce.dto.ProductoResponseDTO;
import com.taller.ecommerce.model.Producto;
import com.taller.ecommerce.model.Proveedor;
import com.taller.ecommerce.repository.ProductoRepository;
import com.taller.ecommerce.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final ProveedorRepository proveedorRepository;
    private final AuditoriaService auditoriaService;

    public ProductoService(ProductoRepository productoRepository,
                           ProveedorRepository proveedorRepository,
                           AuditoriaService auditoriaService) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
        this.auditoriaService = auditoriaService;
    }

    // Crear
    @Transactional
    public ProductoResponseDTO crear(ProductoRequestDTO dto, String correoUsuario) {
        Producto producto = new Producto();
        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());

        if (dto.proveedorId() != null) {
            Proveedor proveedor = proveedorRepository.findById(dto.proveedorId())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            producto.setProveedor(proveedor);
        }

        Producto guardado = productoRepository.save(producto);

        auditoriaService.registrar("CREAR_PRODUCTO", "PRODUCTO", guardado.getId(), correoUsuario, "Producto creado: " + guardado.getNombre());

        return toDTO(guardado);
    }

    // Consultar todos
    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAllByActivoTrue()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Consultar por id
    public ProductoResponseDTO buscarPorId(Long id) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toDTO(producto);
    }

    // Editar
    @Transactional
    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto, String correoUsuario) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setNombre(dto.nombre());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());

        auditoriaService.registrar("ACTUALIZAR_PRODUCTO", "PRODUCTO", id, correoUsuario, "Producto actualizado: " + producto.getNombre());

        return toDTO(productoRepository.save(producto));
    }

    // Soft delete
    @Transactional
    public void eliminar(Long id, String correoUsuario) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setActivo(false); // ← no borra de la BD
        productoRepository.save(producto);

        auditoriaService.registrar("ELIMINAR_PRODUCTO", "PRODUCTO", id, correoUsuario, "Producto eliminado: " + producto.getNombre());
    }

    // Mapper
    private ProductoResponseDTO toDTO(Producto p) {
        return new ProductoResponseDTO(p.getId(), p.getNombre(), p.getPrecio(), p.getStock(), p.getActivo());
    }
}
