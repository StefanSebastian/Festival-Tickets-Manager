﻿using Festival.Model;
using Festival.Repository.Interface;
using Festival.Validation;
using System.Collections.Generic;

namespace Festival.Service
{
    public class ServiceTransaction : IServiceTransaction
    {
        //repo 
        private ITransactionRepository repository;

        //validator 
        private ValidatorTransaction validator;

        //constructor
        public ServiceTransaction(ITransactionRepository repository, ValidatorTransaction validator)
        {
            this.repository = repository;
            this.validator = validator;
        }

        public void save(Transaction transaction)
        {
            repository.save(transaction);
        }

        public void delete(int id)
        {
            repository.delete(id);
        }

        public void update(int id, Transaction transaction)
        {
            repository.update(id, transaction);
        }

        public Transaction getById(int id)
        {
            return repository.getById(id);
        }

        public List<Transaction> getAll()
        {
            return repository.getAll();
        }

        /*
         * saves transaction and generates another id 
         */
        public void saveWithoutId(Transaction transaction)
        {
            repository.saveWithoutId(transaction);
        }
    }
}
