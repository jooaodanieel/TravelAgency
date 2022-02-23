import com.techandteach.curatorship.model.Airline
import com.techandteach.curatorship.model.AirlineRepository
import com.techandteach.framework.database.tables.Airlines
import com.techandteach.utils.types.Name
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.util.*

class AirlineRepositoryImpl(private val db: Database) : AirlineRepository {
    override fun isNameTaken(name: String): Boolean {
        val query = db.from(Airlines)
            .select(Airlines.name)
            .where { Airlines.name eq name }

        var n = 0
        for (row in query) {
            n++
        }

        return n > 0
    }

    override fun add(airline: Airline): Airline {
        val affectedRecords = db.update(Airlines) {
            set(it.name, airline.name.toString())
            where { it.id eq airline.id.toString() }
        }

        if (affectedRecords == 1) return airline

        db.insert(Airlines) {
            set(it.id, airline.id.toString())
            set(it.name, airline.name.toString())
        }

        return airline
    }

    override fun findById(id: UUID): Airline? {
        val airlineRecord = db.from(Airlines)
            .select()
            .where { Airlines.id eq id.toString() }

        var airline: Airline? = null

        for (row in airlineRecord) {
            val id: String = row[Airlines.id] ?: break

            val name: Name = Name.fromString(row[Airlines.name])
            airline = Airline.hydrate(UUID.fromString(id), name)
        }

        return airline
    }

    override fun remove(airline: Airline): Airline? {
        return removeById(airline.id)
    }

    override fun removeById(id: UUID): Airline? {
        val airline = findById(id)

        db.delete(Airlines) { it.id eq id.toString() }

        return airline
    }

}