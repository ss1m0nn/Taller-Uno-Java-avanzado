package com.taller.ecommerce.service;

import com.taller.ecommerce.dto.ItemOrdenRequestDTO;
import com.taller.ecommerce.dto.OrdenRequestDTO;
import com.taller.ecommerce.exception.StockInsuficienteException;
import com.taller.ecommerce.model.ItemOrden;
import com.taller.ecommerce.model.Orden;
import com.taller.ecommerce.model.Producto;
import com.taller.ecommerce.model.Usuario;
import com.taller.ecommerce.repository.ItemOrdenRepository;
import com.taller.ecommerce.repository.OrdenRepository;
import com.taller.ecommerce.repository.ProductoRepository;
import com.taller.ecommerce.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenService {
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final OrdenRepository ordenRepository;
    private final ItemOrdenRepository itemOrdenRepository;

    public OrdenService(UsuarioRepository usuarioRepository,
                        ProductoRepository productoRepository,
                        OrdenRepository ordenRepository,
                        ItemOrdenRepository itemOrdenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.ordenRepository = ordenRepository;
        this.itemOrdenRepository = itemOrdenRepository;
    }

    @Transactional
    public void crearOrden(OrdenRequestDTO request) {

        Usuario usuario = usuarioRepository.findById(request.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setFecha(LocalDateTime.now());
        orden.setEstado("CREADA");

        List<ItemOrden> items = new ArrayList<>();
        double total = 0.0;

        for (ItemOrdenRequestDTO itemDTO : request.items()) {

            Producto producto = productoRepository.findById(itemDTO.productoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < itemDTO.cantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - itemDTO.cantidad());

            double subtotal = producto.getPrecio() * itemDTO.cantidad();
            total += subtotal;

            ItemOrden item = new ItemOrden();
            item.setOrden(orden);
            item.setProducto(producto);
            item.setCantidad(itemDTO.cantidad());
            item.setPrecioUnitario(producto.getPrecio());

            items.add(item);
        }
        orden.setTotal(total);
        ordenRepository.save(orden);
        itemOrdenRepository.saveAll(items);
    }
}
