using Festival.Model;
using Festival.Repository.Interface;
using Festival.Validation;
using System.Collections.Generic;

namespace Festival.Service
{
    public class ServiceUser : IServiceUser
    {
        //repo
        private IUserRepository repository;

        //validator
        private ValidatorUser validator;

        //constructor
        public ServiceUser(IUserRepository repository, ValidatorUser validator)
        {
            this.repository = repository;
            this.validator = validator;
        }

        public void save(User user)
        {
            repository.save(user);
        }

        public void delete(string username)
        {
            repository.delete(username);
        }

        public void update(string username, User user)
        {
            repository.update(username, user);
        }

        public User getById(string username)
        {
            return repository.getById(username);
        }

        public List<User> getAll()
        {
            return repository.getAll();
        }

        public User getByNamePass(string username, string password)
        {
            return repository.getUserFor(username, password);
        }
    }
}
