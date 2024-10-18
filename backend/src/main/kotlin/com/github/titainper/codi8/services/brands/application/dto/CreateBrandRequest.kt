package com.github.titainper.codi8.services.brands.application.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateBrandRequest(
    @field:NotBlank(message = "브랜드 이름은 비어있을 수 없습니다.")
    @field:Size(min = 2, max = 50, message = "브랜드 이름은 2자 이상 50자 이하로 입력해야 합니다.")
    @JsonProperty("name")
    val name: String
)