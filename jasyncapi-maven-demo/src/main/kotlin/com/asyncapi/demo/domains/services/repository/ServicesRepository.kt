package com.asyncapi.demo.domains.services.repository

import com.asyncapi.demo.domains.services.model.Service
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ServicesRepository: CrudRepository<Service, String> {

    @Query(
            value = "UPDATE services SET is_enabled = true WHERE name = ?1",
            nativeQuery = true
    )
    @Modifying(clearAutomatically = true)
    fun enableService(name: String)

    @Query(
            value = "UPDATE services SET is_enabled = false WHERE name = ?1",
            nativeQuery = true
    )
    @Modifying(clearAutomatically = true)
    fun disableService(name: String)

}