using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Festival.Repository
{
    public interface IRepository<E, ID>
    {
        void save(E e);
        void delete(ID id);
        E getById(ID id);
        List<E> getAll();
        void update(ID id, E e);
    }
}
