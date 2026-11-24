package in.org.moneytrail.mapper;

import in.org.moneytrail.dto.TransactionRequest;
import in.org.moneytrail.dto.TransactionResponse;
import in.org.moneytrail.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapperMapStruct {
    @Mapping(
            target = "createdAt",
            expression = "java(LocalDateTime.now())"
    )
    Transaction toEntity(TransactionRequest request);

    TransactionResponse toResponse(Transaction transaction);
}
