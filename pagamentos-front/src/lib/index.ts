import { Api as GeraPagamentoApi } from "./clients/gera-pagamento/GeraPagamentoApi";
import {Api as ProcessaPagamentoApi} from "./clients/processa-pagamento/ProcessaPagamentoApi"

const geraPagamentoClient = new GeraPagamentoApi()
const processaPagamentoClient = new ProcessaPagamentoApi()

export {
    geraPagamentoClient,
    processaPagamentoClient,
}
