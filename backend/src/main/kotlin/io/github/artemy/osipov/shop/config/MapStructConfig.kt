package io.github.artemy.osipov.shop.config

import org.mapstruct.MapperConfig
import org.mapstruct.ReportingPolicy

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.ERROR)
class MapStructConfig 