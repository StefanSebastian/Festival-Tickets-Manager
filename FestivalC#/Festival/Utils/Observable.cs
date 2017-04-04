using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Utils
{
    public interface Observable<E>
    {
        void addObserver(Observer<E> o);
        void removeObserver(Observer<E> o);
        void notifyPushObservers(E e);
    }
}
