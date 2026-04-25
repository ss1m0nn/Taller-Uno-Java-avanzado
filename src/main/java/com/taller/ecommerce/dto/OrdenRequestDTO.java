package com.taller.ecommerce.dto;

import java.util.List;

public record OrdenRequestDTO(Long usuarioId, List<ItemOrdenRequestDTO> items){

}
