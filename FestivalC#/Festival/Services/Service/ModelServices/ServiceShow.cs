using Festival.Model;
using Festival.Repository.Interface;
using Festival.Validation;
using System.Collections.Generic;

namespace Festival.Service
{
    public class ServiceShow : IServiceShow
    {
        //repository
        private IShowRepository repository;

        //validator 
        private ValidatorShow validator;

        public ServiceShow(IShowRepository repository, ValidatorShow validator)
        {
            this.repository = repository;
            this.validator = validator;
        }

        public void save(Show show)
        {
            repository.save(show);
        }

        public void delete(int id)
        {
            repository.delete(id);
        }

        public void update(int id, Show show)
        {
            repository.update(id, show);
        }

        public Show getById(int id)
        {
            return repository.getById(id);
        }

        public List<Show> getAll()
        {
            return repository.getAll();
        }

        //gets all shows for a given artist
        public List<Show> getShowsForArtist(int idArtist)
        {
            return repository.getShowsForArtist(idArtist);
        }


        //gets all shows for a date 
        public List<Show> getShowsForDate(string date)
        {
            return repository.getShowsForDate(date);
        }


    }
}
