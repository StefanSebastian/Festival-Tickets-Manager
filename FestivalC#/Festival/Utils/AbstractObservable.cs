using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Utils
{
    public class AbstractObservable<E> : Observable<E>
    {
        private List<Observer<E>> observers = new List<Observer<E>>();

        public void addObserver(Observer<E> o)
        {
            observers.Add(o);
        }

        public void notifyPushObservers(E e)
        {
            foreach (Observer<E> o in observers)
            {
                o.pushUpdate(e);
            }
        }

        public void removeObserver(Observer<E> o)
        {
            observers.Remove(o);
        }
    }
}
