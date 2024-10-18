package com.github.titainper.codi8.services.brands.domain.event

import org.springframework.context.ApplicationEvent

class BrandUpdatedEvent(id: Long) : ApplicationEvent(id)