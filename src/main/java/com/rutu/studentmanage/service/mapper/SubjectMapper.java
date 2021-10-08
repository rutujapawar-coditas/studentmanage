package com.rutu.studentmanage.service.mapper;

import com.rutu.studentmanage.domain.*;
import com.rutu.studentmanage.service.dto.SubjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Subject} and its DTO {@link SubjectDTO}.
 */
@Mapper(componentModel = "spring", uses = { StudentMapper.class })
public interface SubjectMapper extends EntityMapper<SubjectDTO, Subject> {
    @Mapping(target = "student", source = "student", qualifiedByName = "id")
    SubjectDTO toDto(Subject s);
}
