package br.gov.fatec.comparador.domain

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "courses")
class Course(
    @Column(nullable = false, unique = true)
    var name: String,

    @Column(length = 2000)
    var description: String? = null,

    @OneToMany(
        mappedBy = "course",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @JsonManagedReference
    val disciplines: MutableList<Discipline> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
) {
    fun updateBasicData(newName: String, newDescription: String?) {
        name = newName
        description = newDescription
    }

    fun replaceDisciplines(newDisciplines: Collection<Discipline>) {
        disciplines.clear()
        disciplines.addAll(newDisciplines)
        disciplines.forEach { it.course = this }
    }
}

