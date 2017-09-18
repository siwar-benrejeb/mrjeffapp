package com.mrjeffapp.provider.repository;

import com.mrjeffapp.provider.domain.WorkOrderNote;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WorkOrderNoteRepository extends PagingAndSortingRepository<WorkOrderNote, String> {
}
