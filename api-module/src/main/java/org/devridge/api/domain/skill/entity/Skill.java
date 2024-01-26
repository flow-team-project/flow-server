package org.devridge.api.domain.skill.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.devridge.common.dto.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@SQLDelete(sql = "UPDATE skill SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "skill")
public class Skill extends BaseEntity {

    private String skill;

    @OneToMany(mappedBy = "skill")
    private Set<MemberSkill> memberSkills = new HashSet<>();

    @Builder
    public Skill(String skill) {
        this.skill = skill;
    }
}
