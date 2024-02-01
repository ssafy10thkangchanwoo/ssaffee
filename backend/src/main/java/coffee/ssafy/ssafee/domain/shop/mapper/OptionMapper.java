package coffee.ssafy.ssafee.domain.shop.mapper;

import coffee.ssafy.ssafee.domain.shop.dto.request.OptionRequest;
import coffee.ssafy.ssafee.domain.shop.dto.response.OptionResponse;
import coffee.ssafy.ssafee.domain.shop.entity.Option;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OptionMapper {

    OptionResponse optionToOptionResponse(Option option);

    Option toEntity(OptionRequest optionRequest);

    @AfterMapping
    default void updateFromDto(OptionRequest optionRequest, Option option) {
        option.updateOption(optionRequest);
    }

}
