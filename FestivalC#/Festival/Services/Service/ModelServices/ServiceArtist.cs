﻿using Festival.Model;
using Festival.Repository.Interface;
using Festival.Validation;
using System.Collections.Generic;

namespace Festival.Service
{
    public class ServiceArtist : IServiceArtist
    {
        //repository
        private IArtistRepository repository;

        //validator 
        private ValidatorArtist validator;

        public ServiceArtist(IArtistRepository repository, ValidatorArtist validator)
        {
            this.repository = repository;
            this.validator = validator;
        }

        public void save(Artist artist)
        {
            validator.validate(artist);
            repository.save(artist);
        }

        public void delete(int id)
        {
            repository.delete(id);
        }

        public void update(int id, Artist artist)
        {
            repository.update(id, artist);
        }

        public Artist getById(int id)
        {
            return repository.getById(id);
        }

        public List<Artist> getAll()
        {
            return repository.getAll();
        }
    }
}
