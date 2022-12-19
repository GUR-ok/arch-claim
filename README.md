
#### Инструкция по запуску:
1) `gradle build`
2) `docker build -t gurok/arch_claim .`
3) `docker push gurok/arch_claim`

---

Для локального поднятия кафки: `docker-compose -f .\docker-compose-kafka.yml up`

Пример сообщения в Кафку:

{"event":"CLAIM_DONE", "id":"1f546865-ca9c-4974-b6ba-7495779cf9ec"}

{"event":"UPDATE_CLAIM", "id":"1f546865-ca9c-4974-b6ba-7495779cf9ec",
"updateClaimRequest":{"agreementNumber":123, "brokerageAccountId":"d00bbe8f-2a6d-3eb2-a2c2-b876e053624d", "firstName":"namesome"}
}

### Очистка пространства:

- `helm uninstall gurok/arch_claim`
- `kubectl delete namespace arch-gur`
- `helm uninstall gorelov-kafka`
